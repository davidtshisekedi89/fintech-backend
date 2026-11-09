package com.dvd.paymentservice.services;

import com.dvd.paymentservice.models.FraudFlag;
import com.dvd.paymentservice.models.Transaction;
import com.dvd.paymentservice.repositories.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final FraudDetectionService fraudDetectionService;

    public TransactionService(TransactionRepository transactionRepository,
                              FraudDetectionService fraudDetectionService) {
        this.transactionRepository = transactionRepository;
        this.fraudDetectionService = fraudDetectionService;
    }

    public Transaction createTransaction(Transaction txn) {
        txn.setStatus("PENDING");
        Transaction saved = transactionRepository.save(txn);
        List<FraudFlag> flags = fraudDetectionService.analyzeTransaction(saved);
        saved.setStatus(flags.isEmpty() ? "COMPLETED" : "REVIEW_REQUIRED");
        return transactionRepository.save(saved);
    }
}
