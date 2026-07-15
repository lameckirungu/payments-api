package com.emtech.payments;

import java.util.Map;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        return ResponseEntity.ok(Map.of(
            "status", "UP",
            "service", "payments-api"
        ));
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> processPayment(
            @RequestBody Map<String, Object> request) {

        // Simulated payment processing
        String paymentId = UUID.randomUUID().toString();

        return ResponseEntity.ok(Map.of(
            "paymentId", paymentId,
            "status", "PROCESSED",
            "amount", request.getOrDefault("amount", 0),
            "currency", request.getOrDefault("currency", "KES")
        ));
    }
}