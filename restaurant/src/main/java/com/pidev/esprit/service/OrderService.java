package com.pidev.esprit.service;

import com.pidev.esprit.model.Menu;
import com.pidev.esprit.model.Order;
import com.pidev.esprit.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    public List<Order> getOrdersByMenuName(String menuName) {
        return orderRepository.findByMenuName(menuName);
    }

    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }
    public void deleteOrder(Order order) {
        orderRepository.delete(order);
    }
}