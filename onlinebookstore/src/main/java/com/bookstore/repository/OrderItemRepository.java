package com.bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookstore.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
}

