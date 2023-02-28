package com.pidev.esprit.controller;

import com.pidev.esprit.service.StripeService;
import com.stripe.exception.StripeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@PropertySource("classpath:application.properties")

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    private StripeService stripeService;

    @PostMapping("/charge")
    public ResponseEntity<String> chargeCard(@RequestParam("token") String token,
                                             @RequestParam("amount") Double amount,
                                             @RequestParam("currency") String currency) {

        try {
            stripeService.chargeCreditCard(token, amount, currency);
            return ResponseEntity.ok("Payment processed successfully!");
        } catch (StripeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing payment: " + e.getMessage());
        }
    }
}