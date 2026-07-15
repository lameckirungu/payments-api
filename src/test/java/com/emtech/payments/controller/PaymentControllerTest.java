package com.emtech.payments.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.emtech.payments.PaymentController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PaymentController.class)
class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void healthEndpointReturnsUp() throws Exception {
        mockMvc.perform(get("/api/payments/health"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status").value("UP"))
            .andExpect(jsonPath("$.service").value("payments-api"));
    }

    @Test
    void processPaymentReturnsPaymentId() throws Exception {
        String requestBody = """
            {
                "amount": 1500,
                "currency": "KES"
            }
            """;

        mockMvc.perform(post("/api/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.paymentId").exists())
            .andExpect(jsonPath("$.status").value("PROCESSED"))
            .andExpect(jsonPath("$.amount").value(1500))
            .andExpect(jsonPath("$.currency").value("KES"));
    }
}