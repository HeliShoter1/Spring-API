package com.properties.expense_tracker_api.repontories;

import java.util.List;

import com.properties.expense_tracker_api.Exception.EtBadRequestException;
import com.properties.expense_tracker_api.Exception.EtResourceNotFoundException;
import com.properties.expense_tracker_api.domain.Transaction;

public interface RepontoryTransaction {
    
    List<Transaction> findAll(Integer userId, Integer categoryId);

    Transaction findById(Integer userId, Integer categoryId, Integer transactionId) throws EtResourceNotFoundException;

    Integer create(Integer userId, Integer categoryId, Double amount, String note, Long transactionDate) throws EtBadRequestException;

    void update(Integer usedId, Integer categoryId, Integer transactionId, Transaction transaction) throws EtBadRequestException;

    void remove(Integer userId, Integer categoryId, Integer transactionId) throws EtResourceNotFoundException;
}
