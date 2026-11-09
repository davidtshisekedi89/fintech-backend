package com.dvd.paymentservice.services;

import com.dvd.paymentservice.models.FraudFlag;
import com.dvd.paymentservice.models.Transaction;
import com.dvd.paymentservice.repositories.FraudFlagRepository;
import com.dvd.paymentservice.repositories.TransactionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class FraudDetectionService {
    private final TransactionRepository transactionRepository;
    private final FraudFlagRepository fraudFlagRepository;

    public FraudDetectionService(TransactionRepository transactionRepository,
                                 FraudFlagRepository fraudFlagRepository) {
        this.transactionRepository = transactionRepository;
        this.fraudFlagRepository = fraudFlagRepository;
    }

    public List<FraudFlag> analyzeTransaction(Transaction txn) {
        List<String> reasons = new ArrayList<>();

        if (txn.getAmount().compareTo(BigDecimal.valueOf(1000)) > 0) {
            reasons.add("High transaction amount");
        }

        long recentCount = transactionRepository.countRecentTransactions(
                txn.getSenderAccount(), LocalDateTime.now().minusMinutes(1));
        if (recentCount > 3) {
            reasons.add("Multiple rapid transactions from same sender");
        }

        List<FraudFlag> flags = new ArrayList<>();
        for (String reason : reasons) {
            FraudFlag flag = new FraudFlag();
            flag.setTransactionId(txn.getId());
            flag.setReason(reason);
            flag.setStatus("NEW");
            flags.add(fraudFlagRepository.save(flag));
        }
        return flags;
    }
}
