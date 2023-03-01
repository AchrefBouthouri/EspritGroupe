package com.pidev.esprit.controller;

import com.pidev.esprit.service.SendGridEmailService;
import com.pidev.esprit.service.StripeService;
import com.stripe.exception.StripeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@PropertySource("classpath:application.properties")

@RestController
@RequestMapping("/api/payment")
public class PaymentController {
    @Autowired
    private SendGridEmailService emailService;
    @Autowired
    private StripeService stripeService;



    @PostMapping("/charge")
    public ResponseEntity<String> chargeCard(@RequestParam("token") String token,
                                             @RequestParam("amount") Double amount,
                                             @RequestParam("currency") String currency,
                                             @RequestParam("email") String email) {

        try {
            stripeService.chargeCreditCard(token, amount, currency);
            emailService.sendEmail(email, "Payment Successful", "Your payment was successful!");
            return ResponseEntity.ok("Payment processed successfully!");
        } catch (StripeException | IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing payment: " + e.getMessage());
        }
    }}