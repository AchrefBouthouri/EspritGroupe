package com.pidev.esprit.controller;

import com.pidev.esprit.model.Menu;
import com.pidev.esprit.model.Order;
import com.pidev.esprit.model.Utilisateur;
import com.pidev.esprit.repository.OrderRepository;
import com.pidev.esprit.service.MenuService;
import com.pidev.esprit.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private MenuService menuService;

    @PostMapping("/menu/{menuName}")
    public ResponseEntity<Order> createOrderFromMenu(@PathVariable String menuName, @RequestBody Utilisateur utilisateur) {
        Menu menu = menuService.getMenuByName(menuName);


        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        order.setMenu(menu);
        order.setUtilisateur(utilisateur);


        Order savedOrder = orderService.saveOrder(order);

        return ResponseEntity.ok(savedOrder);
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
}