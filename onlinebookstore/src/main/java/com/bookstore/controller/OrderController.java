package com.bookstore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookstore.model.Order;
import com.bookstore.repository.OrderRepository;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*")
public class OrderController {

    @Autowired
    private OrderRepository orderRepo;

    @GetMapping("/user/{userId}")
public List<Order> getOrdersWithItems(@PathVariable int userId) {
    List<Order> orders = orderRepo.findByUser_Id(userId);
    // Lazy fetch will now populate orderItems if included in response
    return orders;
}

 @GetMapping("/{orderId}")
    public Order getOrderById(@PathVariable int orderId) {
        return orderRepo.findById(orderId).orElseThrow();
    }
}
