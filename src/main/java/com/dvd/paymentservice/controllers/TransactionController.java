package com.dvd.paymentservice.controllers;


import com.dvd.paymentservice.models.Transaction;
import com.dvd.paymentservice.services.TransactionService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService){
        this.transactionService = transactionService;
    }

    @PostMapping
    public Transaction createTransaction(@RequestBody Transaction transaction){
        return transactionService.createTransaction(transaction);
    }


}
