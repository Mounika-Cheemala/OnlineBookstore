package com.bookstore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookstore.model.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByUser_Id(int userId);

}
