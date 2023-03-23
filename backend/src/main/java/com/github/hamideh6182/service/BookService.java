package com.github.hamideh6182.service;

import com.github.hamideh6182.model.Book;
import com.github.hamideh6182.model.BookRequest;
import com.github.hamideh6182.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final IdService idService;
    private final PhotoService photoService;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(String id) {
        return bookRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    public Book addBook(BookRequest bookRequest, MultipartFile photo) {
        if (bookRequest.title() == null) {
            throw new IllegalArgumentException("missing title");
        }
        String photoUri;
        if (photo != null) {
            try {
                photoUri = photoService.uploadPhoto(photo);
            } catch (IOException e) {
                throw new InputMismatchException("The photo upload didn't work: " + e.getMessage());
            }
        } else {
            photoUri = null;
        }
        String id = idService.generateId();
        Book newBook = new Book(
                id,
                bookRequest.title(),
                bookRequest.author(),
                bookRequest.description(),
                bookRequest.copies(),
                bookRequest.copies(),
                bookRequest.category(),
                photoUri
        );
        return bookRepository.save(newBook);
    }
}
