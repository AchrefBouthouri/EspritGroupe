package com.pidev.esprit.controller;


import com.pidev.esprit.service.SmsService;
import com.twilio.exception.TwilioException;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "SMS")
@RestController
@RequestMapping("/api/maintenance")
public class SmsController {

    private final SmsService smsService;

    public SmsController(SmsService smsService) {
        this.smsService = smsService;
    }

    @GetMapping("/send-sms/")
    public String sendSms() throws TwilioException {
        smsService.sendSms("+21626691663", "Veiller Verifier l'intervention N° 283 du 02/03/2023");
        return "SMS sent!";
    }
}
