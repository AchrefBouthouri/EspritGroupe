package com.pidev.esprit.controller;
import com.pidev.esprit.service.JavaEmailService;
import net.glxn.qrgen.javase.QRCode;
import net.glxn.qrgen.core.image.ImageType;
import com.google.zxing.WriterException;
import com.pidev.esprit.model.Menu;
import com.pidev.esprit.model.Order;
import com.pidev.esprit.model.Utilisateur;
import com.pidev.esprit.repository.OrderRepository;
import com.pidev.esprit.service.MenuService;
import com.pidev.esprit.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private JavaEmailService emailService;
    @Autowired
    private OrderService orderService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RestTemplate restTemplate;


    @PostMapping("/menu/{menuName}")
    public ResponseEntity<Order> createOrderFromMenu(@PathVariable String menuName,@RequestParam("email") String email, @RequestBody Utilisateur utilisateur, @RequestParam String token,

                                                     @RequestParam String currency) {
        Menu menu = menuService.getMenuByName(menuName);

        Double price = menuService.getMenuPriceByName(menuName);


        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        order.setMenu(menu);
        order.setUtilisateur(utilisateur);
        order.setPrice(price);


        Order savedOrder = orderService.saveOrder(order, token, price, currency);
        emailService.sendEmail(email, "Payment Successful", "Your payment was successful!");
        return ResponseEntity.ok(savedOrder);
    }



    @GetMapping("/orders/{id}/qrcode")
    public ResponseEntity<byte[]> getQRCodeImage(@PathVariable Long id) throws WriterException {
        Optional<Order> optionalOrder = orderService.getOrderById(id);

        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();

            // Generate QR code for the order number
            ByteArrayOutputStream out = QRCode.from(order.getOrderNumber()).to(ImageType.PNG).stream();
            byte[] qrCodeBytes = out.toByteArray();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);
            headers.setContentLength(qrCodeBytes.length);

            return new ResponseEntity<>(qrCodeBytes, headers, HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/orders/{id}")
    public ResponseEntity<Void> cancelOrder(@PathVariable Long id) {

        Optional<Order> optionalOrder = orderService.getOrderById(id);

        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();

            orderService.deleteOrder(order);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getAll();
        return ResponseEntity.ok(orders);
    }
}
