package dev.bakr.library_manager.controllers;

import dev.bakr.library_manager.requestDtos.BookDtoRequest;
import dev.bakr.library_manager.responseDtos.BookDtoResponse;
import dev.bakr.library_manager.services.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Declares this class as a REST controller (a bean) where return values are auto-converted to JSON.
@RestController
// Sets a base URL path for all endpoints in this controller, following REST grouping principles.
@RequestMapping(path = "api/v1/books")
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
    public List<BookDtoResponse> getBooks() {
        return bookService.getBooks();
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<BookDtoResponse> getBook(@PathVariable Integer id) {
        BookDtoResponse bookDto = bookService.getBook(id);  // this might throw BookNotFoundException
        return ResponseEntity.ok(bookDto);
    }

    // @RequestBody → Tells Spring to deserialize the incoming JSON into a Java object (BookDtoRequest)
    /* @Valid → Tells Spring to trigger Jakarta Bean Validation on the deserialized object, enforcing all declared field
    constraints (e.g., @NotBlank, @Min, etc.) each constraint annotation (@NotBlank, ...) for each field */
    @PostMapping
    public ResponseEntity<BookDtoResponse> addBook(@Valid @RequestBody BookDtoRequest bookDtoRequest) {
        BookDtoResponse bookDto = bookService.addBook(bookDtoRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(bookDto);
    }
}
