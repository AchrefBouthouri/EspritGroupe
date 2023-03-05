package com.pi.bookingservice.service;


import com.pi.bookingservice.model.Booking;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
@EnableScheduling
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private QrCodeGenerator qrCodeGenerator;

    public void sendEmail(String to, String subject, String text) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("sender@example.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        javaMailSender.send(message);
    }

    public void confirmationEmail2(Booking booking) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(booking.getUserEmail());
        message.setSubject("Booking Confirmation");
        message.setText("Dear " + booking.getUserEmail() + ",\n\n"
                + "Your booking has been confirmed for Room : " + booking.getRoomId() + " from "
                + booking.getStartDate().toString() + " to " + booking.getEndDate().toString() + ".\n\n"
                + "Total price: TND" + booking.getTotalPrice() + "\n\n"
                + "Thank you .\n"
                + "-----------");
        mailSender.send(message);
    }
    public void cancellationEmail(Booking booking) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(booking.getUserEmail());
        message.setSubject("Booking Cancellation");
        message.setText("Dear " + booking.getUserEmail() + ",\n\n"
                + "Your booking for Room : " + booking.getRoomId() + " from "
                + booking.getStartDate().toString() + " to " + booking.getEndDate().toString() + " has been cancelled.\n\n"
                + "Thank you.\n"
                + "-----------");
        mailSender.send(message);
    }
    public void renouvReminderEmail(Booking booking) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(booking.getUserEmail());
        message.setSubject("Booking Auto Renewal Reminder");
        message.setText("Dear " + booking.getUserEmail() + ",\n\n"
                + "Your booking (ID: " + booking.getBookingId() + ") is set to renew automatically tomorrow.\n"
                + "If you want to cancel this renewal, please do so before tomorrow.\n\n"
                + "Thank you.\n"
                + "-----------");
        mailSender.send(message);
    }
    public void RenouvEmail(Booking booking) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(booking.getUserEmail());
        message.setSubject("Booking Auto Renewal Confirmation");
        message.setText("Dear " + booking.getUserEmail() + ",\n\n"
                + "Your booking (ID: " + booking.getBookingId() + ") has been renewed automatically for 1 month.\n\n"
                + "Thank you.\n"
                + "-----------");
        mailSender.send(message);
    }


    public void ConfirmationEmail3(Booking booking, String pdfFilePath) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(booking.getUserEmail());
        helper.setSubject("Booking Confirmation");
        helper.setText("Dear " + booking.getUserEmail() + ",\n\n"
                + "Your booking has been confirmed for Room : " + booking.getRoomId() + " from "
                + booking.getStartDate().toString() + " to " + booking.getEndDate().toString() + ".\n\n"
                + "Total price: TND" + booking.getTotalPrice() + "\n\n"
                + "Thank you.\n"
                + "-----------");

        // Add the PDF attachment
        FileSystemResource pdfFile = new FileSystemResource(pdfFilePath);
        helper.addAttachment("booking_qrcode.pdf", pdfFile);

        mailSender.send(message);
    }


}

