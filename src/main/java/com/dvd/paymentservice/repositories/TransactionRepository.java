package com.dvd.paymentservice.repositories;

import com.dvd.paymentservice.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("SELECT COUNT(t) FROM Transaction t WHERE t.senderAccount = :senderAccount AND t.createdAt > :cutoff")
    long countRecentTransactions(@Param("senderAccount") String senderAccount,
                                 @Param("cutoff") LocalDateTime cutoff);
}
