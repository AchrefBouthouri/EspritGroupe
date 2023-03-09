package com.pidev.esprit.service;

import com.twilio.Twilio;
import com.twilio.exception.TwilioException;
import com.twilio.http.TwilioRestClient;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SmsService {

    private final TwilioRestClient twilioRestClient;
    private final String twilioPhoneNumber;

    public SmsService(@Value("${twilio.account_sid}") String accountSid,
                      @Value("${twilio.auth_token}") String authToken,
                      @Value("${twilio.phone_number}") String phoneNumber) {
        twilioRestClient = new TwilioRestClient.Builder(accountSid, authToken).build();
        twilioPhoneNumber = phoneNumber;
    }

    public void sendSms(String toPhoneNumber, String message) throws TwilioException {
        Message sms = Message.creator(new PhoneNumber(toPhoneNumber), new PhoneNumber(twilioPhoneNumber), message).create(twilioRestClient);
        System.out.println("SMS sent: " + sms.getSid());
    }

}

