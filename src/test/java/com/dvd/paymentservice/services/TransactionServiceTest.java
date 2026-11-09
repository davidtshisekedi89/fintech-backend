package com.dvd.paymentservice.services;

import com.dvd.paymentservice.models.FraudFlag;
import com.dvd.paymentservice.models.Transaction;
import com.dvd.paymentservice.repositories.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TransactionServiceTest {
    private TransactionRepository repository;
    private FraudDetectionService fraudDetection;
    private TransactionService service;

    @BeforeEach
    void setUp() {
        repository = mock(TransactionRepository.class);
        fraudDetection = mock(FraudDetectionService.class);
        service = new TransactionService(repository, fraudDetection);
        when(repository.save(any(Transaction.class))).thenAnswer(invocation -> invocation.getArgument(0));
    }

    private Transaction transaction() {
        Transaction txn = new Transaction();
        txn.setAmount(new BigDecimal("50.00"));
        txn.setSenderAccount("sender-1");
        txn.setReceiverAccount("receiver-1");
        return txn;
    }

    @Test
    void completesTransactionWhenNoFraudFlags() {
        Transaction txn = transaction();
        when(fraudDetection.analyzeTransaction(txn)).thenReturn(List.of());
        Transaction saved = service.createTransaction(txn);
        assertEquals("COMPLETED", saved.getStatus());
        verify(repository, times(2)).save(txn);
    }

    @Test
    void holdsFlaggedTransactionForReview() {
        Transaction txn = transaction();
        when(fraudDetection.analyzeTransaction(txn)).thenReturn(List.of(new FraudFlag()));
        Transaction saved = service.createTransaction(txn);
        assertEquals("REVIEW_REQUIRED", saved.getStatus());
        verify(repository, times(2)).save(txn);
    }
}
