package com.properties.expense_tracker_api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.properties.expense_tracker_api.Exception.EtAuthException;
import com.properties.expense_tracker_api.Exception.EtBadRequestException;
import com.properties.expense_tracker_api.Exception.EtResourceNotFoundException;
import com.properties.expense_tracker_api.domain.Category;
import com.properties.expense_tracker_api.domain.Transaction;
import com.properties.expense_tracker_api.repontories.RepontoryTransaction;
import com.properties.expense_tracker_api.repontories.RespontoryCategory;

@Service
@Transactional
public class ServiceTransactionImpl implements ServiceTransaction{

    @Autowired
    RepontoryTransaction repontoryTransaction;

    @Autowired
    RespontoryCategory respontoryCategory;

    @Override
    public List<Transaction> fetchAllTransaction(Integer userId, Integer categoryId){
        return repontoryTransaction.findAll(userId, categoryId);
    }

    @Override
    public Transaction fetchTransactionById(Integer userId, Integer categoryId, Integer transactionId) throws EtAuthException{
        return repontoryTransaction.findById(userId, categoryId, transactionId);
    }

    @Override
    public Transaction addTransaction(Integer userId,Integer categoryId, Double amount, String note, Long transactionDate) throws EtBadRequestException{
        try {
            Category category =  respontoryCategory.findById(userId, categoryId);
            int transactionId = repontoryTransaction.create(userId, categoryId, amount, note, transactionDate);
            return repontoryTransaction.findById(userId, categoryId, transactionId);
        } catch (Exception e) {
            throw new EtBadRequestException("category not valid");
        }
    }

    @Override
    public void updateTransaction(Integer userId, Integer categoryId, Integer transactionId, Transaction transaction) throws EtBadRequestException{
        repontoryTransaction.update(userId, categoryId, transactionId, transaction);
    }

    @Override
    public void removeTransaction(Integer userId, Integer categoryId, Integer transactionId) throws EtResourceNotFoundException{
        repontoryTransaction.remove(userId, categoryId, transactionId);
    }
}
