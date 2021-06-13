package com.daofab.transactions.serviceImpl;

import com.daofab.transactions.exception.DaoFabException;
import com.daofab.transactions.models.Child;
import com.daofab.transactions.models.Parent;
import com.daofab.transactions.services.TransactionService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private ObjectMapper objectmapper;

    private Logger logger = LoggerFactory.getLogger(TransactionService.class);

    @Value("${parent.json.file}")
    private String parentJson;

    @Value("${child.json.file}")
    private String childJson;

    @Value("${data.json.key}")
    private String dataKey;

    @Override
    public List<Parent> getParentTransactions(int size, int page) throws IOException, DaoFabException {

        if (size <= 0 || page <= 0) {
            logger.error("Invalid parameters: size = {}, page = {}", size, page);
            throw new DaoFabException(HttpStatus.BAD_REQUEST.value(), "Invalid parameters");
        }

        InputStream parentInputStream = TypeReference.class.getResourceAsStream(parentJson);
        InputStream childInputStream = TypeReference.class.getResourceAsStream(childJson);

        logger.debug("Reading data from json files");
        Map<String, List<Parent>> parentMap = objectmapper.readValue(parentInputStream, Map.class);
        Map<String, List<Child>> childMap = objectmapper.readValue(childInputStream, Map.class);


        List<Parent> parentList = parentMap.containsKey(dataKey) ? parentMap.get(dataKey) : new ArrayList<>();
        List<Child> childList = childMap.containsKey(dataKey) ? childMap.get(dataKey) : new ArrayList<>();
        parentList = objectmapper.convertValue(parentList, new TypeReference<List<Parent>>() {
        });
        final List<Child> childListFinal = objectmapper.convertValue(childList, new TypeReference<List<Child>>() {
        });

        logger.debug("sorting the parent list");
        parentList.sort((left, right) -> left.getId() - right.getId());

        if (parentList.size() / size < page) {
            logger.error("Invalid page size: total pages = {}, received page = {}", parentList.size() / size, page);
            throw new DaoFabException(HttpStatus.BAD_REQUEST.value(), "Invalid page size");
        }

        int startIndex = (page - 1) * size;
        List<Parent> pagedParentList = parentList.stream().skip(startIndex).limit(size).collect(Collectors.toList());

        logger.debug("calculating total paid amount for parents");
        pagedParentList.forEach(parent -> {
            childListFinal.stream().filter(child -> child.getParentId() == parent.getId())
                    .forEach(child -> parent.setTotalPaidAmount(parent.getTotalPaidAmount() + child.getPaidAmount()));
        });

        logger.info("Successfully completed");
        return pagedParentList;
    }
}
