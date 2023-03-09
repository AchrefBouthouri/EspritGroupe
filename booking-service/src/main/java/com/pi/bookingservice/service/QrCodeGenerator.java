package com.pi.bookingservice.service;


import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BarcodeQRCode;
import com.itextpdf.text.pdf.PdfWriter;
import com.pi.bookingservice.model.Booking;
import com.pi.bookingservice.repository.BookingRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;

@AllArgsConstructor
@NoArgsConstructor
@Component
public class QrCodeGenerator {
    @Autowired
    private BookingRepository bookingRepository;

    public void generatePdfQrCode(Booking booking) throws DocumentException, IOException {
        // Create a new document
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("booking_qrcode.pdf"));

        // Open the document
        document.open();

        // Add a paragraph with the booking details
        Paragraph paragraph = new Paragraph();
        paragraph.add(new Chunk("Booking ID: " + booking.getBookingId() + "\n"));
        paragraph.add(new Chunk("Room ID: " + booking.getRoomId() + "\n"));
        paragraph.add(new Chunk("Start date: " + booking.getStartDate() + "\n"));
        paragraph.add(new Chunk("End date: " + booking.getEndDate() + "\n"));
        paragraph.add(new Chunk("User email: " + booking.getUserEmail() + "\n"));
        paragraph.add(new Chunk("Total price: " + booking.getTotalPrice() + "\n"));
        document.add(paragraph);

        // Generate a QR code with the booking details
        String bookingDetails = "Booking ID: " + booking.getBookingId() + "\n" +
                "Room ID: " + booking.getRoomId() + "\n" +
                "Start date: " + booking.getStartDate() + "\n" +
                "End date: " + booking.getEndDate() + "\n" +
                "User email: " + booking.getUserEmail() + "\n" +
                "Total price: " + booking.getTotalPrice();
        BarcodeQRCode qrCode = new BarcodeQRCode(bookingDetails, 1000, 1000, null);
        Image qrCodeImage = qrCode.getImage();
        qrCodeImage.scaleAbsolute(200, 200);
        document.add(qrCodeImage);

        // Close the document
        document.close();
    }
}