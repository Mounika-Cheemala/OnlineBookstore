package com.bookstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookstore.model.User;
import com.bookstore.repository.UserRepository;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserRepository userRepo;

    // 🔹 Get user by ID (optional, in case you need to fetch again)
    @GetMapping("/{id}")
    public User getUser(@PathVariable int id) {
        return userRepo.findById(id).orElse(null);
    }

    // 🔹 Update user profile (name, password)
    @PutMapping("/{id}")
    public User updateUser(@PathVariable int id, @RequestBody User updatedUser) {
        return userRepo.findById(id).map(user -> {
            user.setName(updatedUser.getName());
            user.setPassword(updatedUser.getPassword()); // Note: hash this in production
            user.setEmail(updatedUser.getEmail());       // Optional: allow email change
            user.setRole(updatedUser.getRole());         // Optional: not typically user-editable
            return userRepo.save(user);
        }).orElse(null);
    }
}
