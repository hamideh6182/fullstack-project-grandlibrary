package com.github.hamideh6182.service;

import com.github.hamideh6182.exception.BookNotFoundException;
import com.github.hamideh6182.exception.UnauthorizedException;
import com.github.hamideh6182.model.Book;
import com.github.hamideh6182.model.BookRequest;
import com.github.hamideh6182.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.InputMismatchException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final IdService idService;
    private final PhotoService photoService;
    private final MongoUserDetailsService mongoUserDetailsService;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(String id) {
        return bookRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    public Book addBook(BookRequest bookRequest, MultipartFile photo, Principal principal) {
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
        String adminId = mongoUserDetailsService.getMe(principal).id();
        Book newBook = new Book(
                id,
                bookRequest.title(),
                bookRequest.author(),
                bookRequest.description(),
                bookRequest.copies(),
                bookRequest.copies(),
                bookRequest.category(),
                photoUri,
                adminId
        );
        return bookRepository.save(newBook);
    }

    public Book deleteBook(String id, Principal principal) {
        String adminId = mongoUserDetailsService.getMe(principal).id();
        Optional<Book> book = bookRepository.findById(id);
        if (book.isEmpty()) {
            throw new BookNotFoundException("Book not found");
        }
        if (!book.get().userId().equals(adminId)) {
            throw new UnauthorizedException("Administration page");
        }
        bookRepository.delete(book.get());
        return book.get();
    }

    public Book increaseBookQuantity(String id, Principal principal) {
        String adminId = mongoUserDetailsService.getMe(principal).id();
        Optional<Book> book = bookRepository.findById(id);
        if (book.isEmpty()) {
            throw new BookNotFoundException("Book not found");
        }
        if (!book.get().userId().equals(adminId)) {
            throw new UnauthorizedException("Administration page only");
        }
        int newCopies = book.get().copies() + 1;
        int newCopiesAvailable = book.get().copiesAvailable() + 1;
        Book newBook = new Book(
                id,
                book.get().title(),
                book.get().author(),
                book.get().description(),
                newCopies,
                newCopiesAvailable,
                book.get().category(),
                book.get().img(),
                adminId
        );
        return bookRepository.save(newBook);
    }

    public Book decreaseBookQuantity(String id, Principal principal) {
        String adminId = mongoUserDetailsService.getMe(principal).id();
        Optional<Book> book = bookRepository.findById(id);
        if (book.isEmpty() || book.get().copies() <= 0 || book.get().copiesAvailable() <= 0) {
            throw new BookNotFoundException("Book not found or quantity locked");
        }
        if (!book.get().userId().equals(adminId)) {
            throw new UnauthorizedException("Only Administration");
        }
        int newCopies = book.get().copies() - 1;
        int newCopiesAvailable = book.get().copiesAvailable() - 1;
        Book newBook = new Book(
                id,
                book.get().title(),
                book.get().author(),
                book.get().description(),
                newCopies,
                newCopiesAvailable,
                book.get().category(),
                book.get().img(),
                adminId
        );
        return bookRepository.save(newBook);
    }
}
