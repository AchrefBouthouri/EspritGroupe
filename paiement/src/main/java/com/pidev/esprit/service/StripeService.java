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

    @Value("stripe.publicKey")
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
//public void chargeCreditCard(String cardNumber, Integer expMonth, Integer expYear, String cvc, Double amount, String currency) throws StripeException {
//
//    Stripe.apiKey = apiKey;
//
//    Map<String, Object> card = new HashMap<>();
//    card.put("number", cardNumber);
//    card.put("exp_month", expMonth);
//    card.put("exp_year", expYear);
//    card.put("cvc", cvc);
//
//    Map<String, Object> params = new HashMap<>();
//    params.put("amount", Math.round(amount * 100));
//    params.put("currency", currency);
//    params.put("source", card);
//    params.put("description", "Payment for order 123");
//
//    Charge charge = Charge.create(params);
//}
}