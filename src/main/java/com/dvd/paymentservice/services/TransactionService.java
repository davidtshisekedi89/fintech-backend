package com.dvd.paymentservice.services;

import com.dvd.paymentservice.models.Transaction;
import com.dvd.paymentservice.repositories.FraudFlagRepository;
import com.dvd.paymentservice.repositories.TransactionRepository;

public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final FraudDetectionService fraudDetectionService;

    public TransactionService(TransactionRepository transactionRepository,
                              FraudDetectionService fraudDetectionService){
        this.transactionRepository = transactionRepository;
        this.fraudDetectionService = fraudDetectionService;
    }

    public Transaction createTransaction(Transaction txn){
        txn.setStatus("PENDING");

        // Save new transaction
        Transaction saved = transactionRepository.save(txn);

        //Run fraud checks
        fraudDetectionService.analyzeTransaction(saved);

        // Update status if needed
        saved.setStatus("COMPLETED");
        return transactionRepository.save(saved);
    }
}
