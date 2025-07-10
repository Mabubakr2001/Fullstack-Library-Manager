package dev.bakr.library_manager.controllers;

import dev.bakr.library_manager.dtos.BookDTO;
import dev.bakr.library_manager.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// Declares this class as a REST controller (a bean) where return values are auto-converted to JSON.
@RestController
// Sets a base URL path for all endpoints in this controller, following REST grouping principles.
@RequestMapping(path = "/books")
public class BookController {
    private final BookService bookService;

    /* Demonstrates Inversion of Control (IoC) via constructor-based Dependency Injection
    (DI). The control of object creation and wiring is inverted: from me writing new
    manually, to Spring doing it for me automatically. */
    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // Handles HTTP GET requests; aligns with REST where GET retrieves resources.
    @GetMapping
    public List<BookDTO> getBooks() {
        return bookService.getBooks();
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<BookDTO> getBook(@PathVariable Integer id) {
        return bookService.getBook(id);
    }
}
