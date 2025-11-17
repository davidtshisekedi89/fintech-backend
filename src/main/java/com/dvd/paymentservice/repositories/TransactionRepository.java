package com.dvd.paymentservice.repositories;
import com.dvd.paymentservice.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
   @Query("SELECT COUNT(t)FROM transaction t WHERE t.senderAccount = :senderAccount AND t.createAt > CURRENT_TIMESTAMP - INTERVAL 1 MINUTE")
    long countRecentTransactions(String senderAccount);
}
