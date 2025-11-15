# FinTech Backend Service

## Overview
A Spring Boot backend project that simulates a payment processing and fraud detection system.  
Includes payment authorization, reconciliation, fraud flagging, and admin dashboard endpoints.

## Tech Stack
- Java 17, Spring Boot
- MySQL
- Hibernate/JPA
- Docker
- REST APIs, JWT Security

## Features
- Secure payment authorization
- Transaction storage and reconciliation
- Fraud detection based on rules
- Webhook alerts for suspicious transactions
- Admin dashboard endpoints
- Scheduler jobs for reconciliation and fraud review

## How to Run
1. Set up MySQL and create a database `fintech_db`
2. Update `application.properties` with your DB credentials
3. Run `mvn clean package`
4. Start with `java -jar target/fintech-backend.jar`
