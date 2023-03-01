package com.pidev.esprit.repository;
import com.pidev.esprit.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByMenuName(String menuName);
    List<Order> getOrderById(Long id);


}