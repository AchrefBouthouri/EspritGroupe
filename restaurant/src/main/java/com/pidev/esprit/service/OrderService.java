package com.pidev.esprit.service;

import com.pidev.esprit.config.WebClientConfig;
import com.pidev.esprit.model.Capacity;
import com.pidev.esprit.model.Menu;
import com.pidev.esprit.model.Order;
import com.pidev.esprit.repository.CapacityRepository;
import com.pidev.esprit.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
@EnableScheduling
public class OrderService {

    @Value("${paiement-service.url}")
    private String paymentServiceUrl;

    @Autowired
    private final WebClient.Builder webClientBuilder;

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CapacityRepository capacityRepository;


    public Order saveOrder(Order order, String token, Double amount, String currency) {

        Capacity capacity = capacityRepository.findById(1L).get();

        if(capacity.getValue()==0){
            throw new RuntimeException("Capacity is full");


        }else {
        try {
            int c=capacity.getValue();
            chargeCreditCard(token, amount, currency);
           c-=1;
           capacity.setValue(c);

           capacityRepository.save(capacity);

           return orderRepository.save(order);

        }
        catch (Exception e) {
            throw new RuntimeException("Error processing payment and saving order: " + e.getMessage());
        }
    }

    }

    public void deleteOrder(Order order) {
        orderRepository.delete(order);
        Capacity capacity = capacityRepository.findById(1L).get();
        int c=capacity.getValue();
        c+=1;
        capacity.setValue(c);
        capacityRepository.save(capacity);

    }
    private void chargeCreditCard(String token, Double amount, String currency) {
        String url = paymentServiceUrl + "/api/payment/charge?token={token}&amount={amount}&currency={currency}";

        try {
            webClientBuilder.build()
                    .post()
                    .uri(url, token, amount, currency)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        } catch (Exception e) {
            throw new RuntimeException("Payment not completed");
        }
    }

    public List<Order> getOrdersByMenuName(String menuName) {
        return orderRepository.findByMenuName(menuName);
    }

    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    public List<Order> getAll() {
        return orderRepository.findAll();
    }


    @Scheduled(cron = "0 0 0 * * ?")
    public void deleteAllOrders() {
        log.info("Deleting all orders from the database");
        orderRepository.deleteAll();
    }


}