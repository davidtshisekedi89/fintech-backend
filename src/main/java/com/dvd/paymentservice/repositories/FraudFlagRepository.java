package com.dvd.paymentservice.repositories;

import com.dvd.paymentservice.models.FraudFlag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FraudFlagRepository extends JpaRepository<FraudFlag, Long> {

    List<FraudFlag> findByStatus(String status);
    List<FraudFlag> findByTransactionId(Long transactionId);
}
