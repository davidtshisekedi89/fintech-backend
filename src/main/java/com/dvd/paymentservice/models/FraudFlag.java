package com.dvd.paymentservice.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Fraud_Flags")
public class FraudFlag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long transactionId;// link to transaction
    private String reason; // e.g., "High Amount", "Suspicious Account"
    private String status; // NEW, REVIEWED, CLEARED

    private LocalDateTime flaggedAt;

    @PrePersist
    protected void onFlag(){
        flaggedAt = LocalDateTime.now();
    }
    //Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getFlaggedAt() {
        return flaggedAt;
    }

    public void setFlaggedAt(LocalDateTime flaggedAt) {
        this.flaggedAt = flaggedAt;
    }
}
