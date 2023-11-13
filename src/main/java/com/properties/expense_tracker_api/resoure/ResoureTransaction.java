package com.properties.expense_tracker_api.resoure;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.properties.expense_tracker_api.domain.Transaction;
import com.properties.expense_tracker_api.service.ServiceTransaction;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/categories/{categoryId}/transactions")
public class ResoureTransaction {
    @Autowired
    ServiceTransaction serviceTransaction;

    @PostMapping("")
    public ResponseEntity<Transaction> addTransaction(HttpServletRequest request,@PathVariable("categoryId") Integer categoryId, @RequestBody Map<String,Object> mapTransaction){
        int userId = (Integer)request.getAttribute("userId");
        Double amount = Double.valueOf(mapTransaction.get("amount").toString());
        String note = (String) mapTransaction.get("note");
        Long transactionDate = (Long) mapTransaction.get("transactionDate");
        Transaction transaction = serviceTransaction.addTransaction(userId, categoryId, amount, note, transactionDate);
        return new ResponseEntity<>(transaction,HttpStatus.CREATED);
    }
    
    @GetMapping("{transactionId}")
    public ResponseEntity<Transaction> getTransaction(HttpServletRequest request,@PathVariable("categoryId") Integer categoryId, @PathVariable("transactionId") Integer transactionId){
        int userId = (Integer)request.getAttribute("userId");
        Transaction transaction = serviceTransaction.fetchTransactionById(userId, categoryId, transactionId);
        return new ResponseEntity<>(transaction,HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<List<Transaction>> getTransactions(HttpServletRequest request,@PathVariable("categoryId") Integer categoryId){
        int userId = (Integer)request.getAttribute("userId");
        List<Transaction> listTransactions = serviceTransaction.fetchAllTransaction(userId, categoryId);
        return new ResponseEntity<>(listTransactions,HttpStatus.OK);
    }
    
    @PutMapping("/{transactionId}")
    public ResponseEntity<String> updateTransaction(HttpServletRequest request,@PathVariable("categoryId")Integer categoryId,@PathVariable("transactionId") Integer transactionId,@RequestBody Transaction transaction){
        int userId = (Integer)request.getAttribute("userId");
        serviceTransaction.updateTransaction(userId, categoryId, transactionId, transaction);
        return new ResponseEntity<>("update successfully",HttpStatus.OK);
    }

    @DeleteMapping("/{transactionId}")
    public ResponseEntity<String> removeTransaction(HttpServletRequest request,@PathVariable("categoryId")Integer categoryId,@PathVariable("transactionId")Integer transactionId){
        int userId = (Integer)request.getAttribute("userId");
        serviceTransaction.removeTransaction(userId, categoryId, transactionId);
        return new ResponseEntity<>("delete successfully",HttpStatus.OK);
    }
}
