package com.dvd.paymentservice.controllers;

import com.dvd.paymentservice.models.FraudFlag;
import com.dvd.paymentservice.repositories.FraudFlagRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/fraud-flags")
public class FraudFlagController {
    private final FraudFlagRepository fraudFlagRepository;

    public FraudFlagController(FraudFlagRepository fraudFlagRepository) {
        this.fraudFlagRepository = fraudFlagRepository;
    }

    @GetMapping("/{status}")
    public List<FraudFlag> getFraudByStatus(@PathVariable String status) {
        return fraudFlagRepository.findByStatus(status.toUpperCase());
    }
}
