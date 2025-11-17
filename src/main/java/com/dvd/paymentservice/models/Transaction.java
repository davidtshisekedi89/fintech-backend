package com.dvd.paymentservice.models;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "Transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String senderAccount;
    private String receiverAccount;

    private BigDecimal amount;

    private String status; // PENDING, COMPLETED, FAILED

    private LocalDateTime createdAt;
    private LocalDateTime updateAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updateAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updateAt = LocalDateTime.now();
    }

    // Getters and Setters

    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}

    public String getSenderAccount() {return senderAccount;}

    public void setSenderAccount(String senderAccount) {this.senderAccount = senderAccount;}

    public BigDecimal getAmount() {return amount;}

    public void setAmount(BigDecimal amount) {this.amount = amount;}

    public String getStatus() {return status;}

    public void setStatus(String status) {this.status = status;}

    public String getReceiverAccount() {return receiverAccount;}

    public void setReceiverAccount(String receiverAccount) {this.receiverAccount = receiverAccount;}

    public LocalDateTime getCreatedAt() {return createdAt;}

    public void setCreatedAt(LocalDateTime createdAt) {this.createdAt = createdAt;}

    public LocalDateTime getUpdateAt() {return updateAt;}

    public void setUpdateAt(LocalDateTime updateAt) {this.updateAt = updateAt;}
    //@PrePersist and @PreUpdate automatically set timestamps.
    //BigDecimal is best for money
    //Status is a simple String for now; later we can make it an Enum
}
