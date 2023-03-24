package com.github.hamideh6182.controller;

import com.github.hamideh6182.model.Book;
import com.github.hamideh6182.model.BookRequest;
import com.github.hamideh6182.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public Book addBook(@RequestPart("bookRequest") BookRequest bookRequest, @RequestPart(value = "file", required = false) MultipartFile photo) {
        return bookService.addBook(bookRequest, photo);
    }

    @DeleteMapping("{id}")
    public Book deleteBook(@PathVariable String id) {
        return bookService.deleteBook(id);
    }

    @PutMapping("/quantity/increase/{id}")
    public Book increaseBookQuantity(@PathVariable String id) {
        return bookService.increaseBookQuantity(id);
    }
}
