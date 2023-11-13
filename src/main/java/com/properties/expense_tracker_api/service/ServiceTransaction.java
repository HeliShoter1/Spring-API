package com.properties.expense_tracker_api.service;

import java.util.List;

import com.properties.expense_tracker_api.Exception.EtAuthException;
import com.properties.expense_tracker_api.Exception.EtBadRequestException;
import com.properties.expense_tracker_api.Exception.EtResourceNotFoundException;
import com.properties.expense_tracker_api.domain.Transaction;

public interface ServiceTransaction {
    
    List<Transaction> fetchAllTransaction(Integer userId, Integer categoryId);

    Transaction fetchTransactionById(Integer userId, Integer categoryId, Integer TransactionId) throws EtAuthException;

    Transaction addTransaction(Integer userId,Integer categoryId, Double amount, String note, Long transactionDate) throws EtBadRequestException;

    void updateTransaction(Integer userId, Integer categoryId, Integer transactionId, Transaction transaction) throws EtBadRequestException;

    void removeTransaction(Integer userId, Integer categoryId, Integer transactionId) throws EtResourceNotFoundException;
}
