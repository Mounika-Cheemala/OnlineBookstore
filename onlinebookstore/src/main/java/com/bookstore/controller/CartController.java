package com.bookstore.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookstore.model.Book;
import com.bookstore.model.CartItem;
import com.bookstore.model.Order;
import com.bookstore.model.OrderItem;
import com.bookstore.model.User;
import com.bookstore.repository.BookRepository;
import com.bookstore.repository.CartItemRepository;
import com.bookstore.repository.OrderItemRepository;
import com.bookstore.repository.OrderRepository;
import com.bookstore.repository.UserRepository;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = "*")
public class CartController {

    @Autowired
    private CartItemRepository cartRepo;


    @Autowired
    private BookRepository bookRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private OrderRepository orderRepo;

    @Autowired
    private OrderItemRepository orderItemRepo;

   @PostMapping("/add")
public CartItem addToCart(@RequestBody Map<String, Object> payload) {
    try {
        int userId = Integer.parseInt(payload.get("userId").toString());
        int bookId = Integer.parseInt(payload.get("bookId").toString());
        int quantity = Integer.parseInt(payload.get("quantity").toString());

        System.out.println("userId: " + userId + ", bookId: " + bookId + ", quantity: " + quantity);

        Book book = bookRepo.findById(bookId).orElse(null);
        User user = userRepo.findById(userId).orElse(null);

        if (book == null || user == null) {
            throw new RuntimeException("Invalid book or user");
        }

        CartItem item = new CartItem();
        item.setBook(book);
        item.setUser(user);
        item.setQuantity(quantity);

        return cartRepo.save(item);
    } catch (Exception e) {
        e.printStackTrace();
        throw new RuntimeException("Error adding to cart: " + e.getMessage());
    }
}




    @GetMapping("/user/{userId}")
    public List<CartItem> getCartForUser(@PathVariable int userId) {
        return cartRepo.findByUser_Id(userId);
    }
    @DeleteMapping("/remove/{itemId}")
    public void removeFromCart(@PathVariable int itemId) {
    cartRepo.deleteById(itemId);
    }
  @PostMapping("/place-order/{userId}")
public String placeOrder(@PathVariable int userId, @RequestBody Map<String, String> data) {
    List<CartItem> cartItems = cartRepo.findByUser_Id(userId);

    if (cartItems.isEmpty()) {
        return "Cart is empty!";
    }

    String fullName = data.get("fullName");
    String address = data.get("address");
    String phone = data.get("phone");
    String payment = data.get("payment");

    System.out.println("📦 Full Name: " + fullName);
    System.out.println("📞 Phone: " + phone);
    System.out.println("🏠 Address: " + address);
    System.out.println("💳 Payment Method: " + payment);

    double total = 0;
    for (CartItem item : cartItems) {
        total += item.getBook().getPrice() * item.getQuantity();
    }

    Order order = new Order();
    order.setUser(cartItems.get(0).getUser());
    order.setTotal(total);
    order.setPhoneNumber(data.get("phone"));
    order.setPaymentMethod(data.get("payment"));
    order.setOrderDate(LocalDateTime.now());

    orderRepo.save(order);

    for (CartItem item : cartItems) {
        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        orderItem.setBook(item.getBook());
        orderItem.setQuantity(item.getQuantity());
        orderItem.setPrice(item.getBook().getPrice());

        orderItemRepo.save(orderItem);
    }

    cartRepo.deleteAll(cartItems);

    return "Order placed successfully!";
}




}
