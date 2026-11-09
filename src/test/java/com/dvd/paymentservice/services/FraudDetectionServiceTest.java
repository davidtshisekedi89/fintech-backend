package com.dvd.paymentservice.services;

import com.dvd.paymentservice.models.FraudFlag;
import com.dvd.paymentservice.models.Transaction;
import com.dvd.paymentservice.repositories.FraudFlagRepository;
import com.dvd.paymentservice.repositories.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class FraudDetectionServiceTest {
    private TransactionRepository transactions;
    private FraudFlagRepository flags;
    private FraudDetectionService service;

    @BeforeEach
    void setUp() {
        transactions = mock(TransactionRepository.class);
        flags = mock(FraudFlagRepository.class);
        service = new FraudDetectionService(transactions, flags);
        when(flags.save(any(FraudFlag.class))).thenAnswer(invocation -> invocation.getArgument(0));
    }

    private Transaction transaction(String amount) {
        Transaction txn = new Transaction();
        txn.setId(42L);
        txn.setSenderAccount("sender-1");
        txn.setAmount(new BigDecimal(amount));
        return txn;
    }

    @Test
    void highAmountCreatesFraudFlag() {
        List<FraudFlag> result = service.analyzeTransaction(transaction("1000.01"));
        assertEquals(1, result.size());
        assertEquals("High transaction amount", result.get(0).getReason());
        assertEquals("NEW", result.get(0).getStatus());
        assertEquals(42L, result.get(0).getTransactionId());
    }

    @Test
    void exactlyAtThresholdDoesNotFlag() {
        assertTrue(service.analyzeTransaction(transaction("1000.00")).isEmpty());
        verify(flags, never()).save(any());
    }

    @Test
    void rapidTransactionsCreateFlag() {
        when(transactions.countRecentTransactions(eq("sender-1"), any(LocalDateTime.class))).thenReturn(4L);
        List<FraudFlag> result = service.analyzeTransaction(transaction("25.00"));
        assertEquals(1, result.size());
        assertTrue(result.get(0).getReason().contains("rapid"));
    }

    @Test
    void bothRulesCanCreateSeparateFlags() {
        when(transactions.countRecentTransactions(eq("sender-1"), any(LocalDateTime.class))).thenReturn(4L);
        assertEquals(2, service.analyzeTransaction(transaction("1500.00")).size());
        verify(flags, times(2)).save(any(FraudFlag.class));
    }
}
