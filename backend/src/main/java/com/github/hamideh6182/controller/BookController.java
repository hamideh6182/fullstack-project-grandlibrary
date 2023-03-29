package com.github.hamideh6182.controller;

import com.github.hamideh6182.model.Book;
import com.github.hamideh6182.model.BookRequest;
import com.github.hamideh6182.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/books")
public class BookController {
    public final BookService bookService;

    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("{id}")
    public Book getBookById(@PathVariable String id) {
        return bookService.getBookById(id);
    }

    @PostMapping
    public Book addBook(@RequestPart("bookRequest") BookRequest bookRequest, @RequestPart(value = "file", required = false) MultipartFile photo, Principal principal) {
        return bookService.addBook(bookRequest, photo, principal);
    }

    @DeleteMapping("{id}")
    public Book deleteBook(@PathVariable String id, Principal principal) {
        return bookService.deleteBook(id, principal);
    }

    @PutMapping("/quantity/increase/{id}")
    public Book increaseBookQuantity(@PathVariable String id, Principal principal) {
        return bookService.increaseBookQuantity(id, principal);
    }

    @PutMapping("/quantity/decrease/{id}")
    public Book decreaseBookQuantity(@PathVariable String id, Principal principal) {
        return bookService.decreaseBookQuantity(id, principal);
    }
}
