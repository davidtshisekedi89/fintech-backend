package com.dvd.paymentservice.services;

import com.dvd.paymentservice.models.FraudFlag;
import com.dvd.paymentservice.models.Transaction;
import com.dvd.paymentservice.repositories.FraudFlagRepository;
import com.dvd.paymentservice.repositories.TransactionRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class FraudDetectionService {

    private final TransactionRepository transactionRepository;
    private final FraudFlagRepository fraudFlagRepository;

    public FraudDetectionService(TransactionRepository transactionRepository,
                                 FraudFlagRepository fraudFlagRepository) {
        this.transactionRepository = transactionRepository;
        this.fraudFlagRepository = fraudFlagRepository;
    }

    public List<FraudFlag>analyzeTransaction(Transaction txn){
        List<String> reasons = new ArrayList<>();

        // Rule 1: High amount
        if(txn.getAmount().compareTo(BigDecimal.valueOf(1000)) > 0) {
            reasons.add("High transaction amount");
        }

        // Rule 2: Too many fast transactions
        long recentCount = transactionRepository.countRecentTransactions(txn.getSenderAccount());
        if(recentCount > 3){
            reasons.add("Multiple rapid transaction from same sender");
        }

        List<FraudFlag>flags = new ArrayList<>();

        // Create flags if validations exist
        for(String reason : reasons){
            FraudFlag flag = new FraudFlag();
            flag.setTransactionId(txn.getId());
            flag.setReason(reason);
            flag.setStatus("NEW");
            fraudFlagRepository.save(flag);

            flags.add(flag);
        }

        return flags;
    }
}
