package com.bookstore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookstore.model.Book;
import com.bookstore.repository.BookRepository;

@RestController
@RequestMapping("/api/books")
@CrossOrigin(origins = "*")
public class BookController {

    @Autowired
    private BookRepository bookRepo;

    @GetMapping
    public List<Book> getAllBooks() {
        return bookRepo.findAll();
    }

    @PostMapping
    public Book addBook(@RequestBody Book book) {
        return bookRepo.save(book);
    }

    @GetMapping("/{id}")
    public Book getBookById(@PathVariable int id) {
        return bookRepo.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
public Book updateBook(@PathVariable int id, @RequestBody Book updatedBook) {
    Book book = bookRepo.findById(id).orElseThrow();
    book.setTitle(updatedBook.getTitle());
    book.setAuthor(updatedBook.getAuthor());
    book.setPrice(updatedBook.getPrice());
    return bookRepo.save(book);
}

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable int id) {
        bookRepo.deleteById(id);
    }
}
