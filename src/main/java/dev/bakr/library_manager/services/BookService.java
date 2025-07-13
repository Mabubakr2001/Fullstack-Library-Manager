package dev.bakr.library_manager.services;

import dev.bakr.library_manager.enums.BookStatus;
import dev.bakr.library_manager.exceptions.InvalidBookStatusException;
import dev.bakr.library_manager.mappers.BookMapper;
import dev.bakr.library_manager.repositories.BookRepository;
import dev.bakr.library_manager.requestDtos.BookDtoRequest;
import dev.bakr.library_manager.responseDtos.BookDtoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/* This class is now a Spring Bean (In Spring, a bean is any class that is managed by
the Spring IoC container — i.e., it’s created, configured, injected, and destroyed by
Spring, not by you.) Because of the @Service annotation. It will be instantiated and
managed automatically by the Spring IoC container (ApplicationContext), and can be
injected into other components (like controllers) as a dependency. */
@Service
public class BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    // this annotation also can be removed if it's a constructor injection like this.
    @Autowired
    public BookService(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    public List<BookDtoResponse> getBooks() {
        return bookRepository.findAll().stream().map(bookMapper::toDto).collect(Collectors.toList());
    }

    /* Returns the bookDTO by ID using ResponseEntity (represents the entire HTTP response), which lets us control the
    HTTP status and body. Sends 200 OK if found, or throws an error if not. */
    public ResponseEntity<BookDtoResponse> getBook(Integer id) {
        /* findById returns Optional<Book>, a container that may or may not hold a value (Book). If a Book is found,
        map. Otherwise, return null. */
        var book = bookRepository.findById(id).orElse(null);
        if (book == null) {
            // return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return ResponseEntity.notFound().build();
        }
        // return new ResponseEntity<>(bookMapper.toDto(book), HttpStatus.OK);
        return ResponseEntity.ok(bookMapper.toDto(book));
    }

    public ResponseEntity<BookDtoResponse> addBook(BookDtoRequest bookDtoRequest) {
        var givenBookStatus = bookDtoRequest.status();
        boolean validStatus = false;
        // loop over the enum values (constants) and compare each value with the give book status
        for (int i = 0; i < BookStatus.values().length; i++) {
            var statusConstant = BookStatus.values()[i];
            if (statusConstant.name().equalsIgnoreCase(givenBookStatus)) {
                validStatus = true;
                break;
            }
        }
        if (!validStatus) {
            throw new InvalidBookStatusException("Invalid book status: '" + givenBookStatus + "'. Allowed values are: " + Arrays.toString(
                    BookStatus.values()) + ", and can be lowercase.");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }
}
