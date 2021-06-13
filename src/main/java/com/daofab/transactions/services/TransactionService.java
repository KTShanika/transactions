package com.daofab.transactions.services;

import com.daofab.transactions.exception.DaoFabException;
import com.daofab.transactions.models.Parent;

import java.io.IOException;
import java.util.List;

public interface TransactionService {

    public List<Parent> getParentTransactions(int size, int page) throws IOException, DaoFabException;
}
