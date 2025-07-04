package com.bookstore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookstore.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    List<CartItem> findByUser_Id(int userId);
}
