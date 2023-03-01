package com.pidev.esprit.service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentConfirmParams;
import com.stripe.param.PaymentIntentCreateParams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class StripeService {

    @Value("sk_test_51MfqdlGre5IWbhAc8crtVV3tzR0IL1WnvjAVmePNz8YtUmq1pKDlz4ITEq0kc07Dpo6ynYA1J5iZ37QfnjOdAo8B00FVN0TQmi")
    private String apiKey;

    public void chargeCreditCard(String token, Double amount, String currency) throws StripeException {

        Stripe.apiKey = apiKey;

        Map<String, Object> params = new HashMap<>();
        params.put("amount", Math.round(amount * 100));
        params.put("currency", currency);
        params.put("source", token);
        params.put("description", "Payment for : ...");

        Charge charge = Charge.create(params);
    }
}