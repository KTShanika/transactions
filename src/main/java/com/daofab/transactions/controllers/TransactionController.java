package com.daofab.transactions.controllers;

import com.daofab.transactions.exception.DaoFabException;
import com.daofab.transactions.models.Child;
import com.daofab.transactions.models.Parent;
import com.daofab.transactions.serviceImpl.TransactionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class TransactionController {


    @Autowired
    private TransactionServiceImpl transactionService;

    @GetMapping(value = "/api/parent_transactions")
    public ResponseEntity<List<Parent>> getParent(@RequestParam(value = "size", defaultValue = "2", required = false) int size,
                                                  @RequestParam(value = "page", defaultValue = "1", required = false) int page)
            throws IOException, DaoFabException {


        return new ResponseEntity<>(transactionService.getParentTransactions(size, page),HttpStatus.ACCEPTED);
    }

    @GetMapping(value = "/test/{id}" )
    public ResponseEntity<String> test(@PathVariable(value = "id") int id){
        System.out.println(id);
        return new ResponseEntity<String>("aaaa",HttpStatus.ACCEPTED);
    }
    @GetMapping(value = "/api/parent/{id}/children")
    public ResponseEntity<List<Child>> getChildrenByParent(@PathVariable(value = "id") int id)
            throws IOException, DaoFabException {
        System.out.println(id);
        return new ResponseEntity<>(transactionService.getChildrenByParentId(id),HttpStatus.ACCEPTED);
    }
}
