package com.pidev.esprit.service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class StripeService {

    @Value("sk_test_51MgEnVJLaSjV8kIt3mFd1e1BPC9sB8KeIoOIgaMSEKi37gsm84t8nAVTURKFskqHLL2r1jP0JH3HWgFzdRpIuQjj00pAb5yKIb")
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