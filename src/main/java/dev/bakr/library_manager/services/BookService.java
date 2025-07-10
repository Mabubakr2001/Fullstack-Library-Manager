package dev.bakr.library_manager.services;

import dev.bakr.library_manager.BookDTOMapper;
import dev.bakr.library_manager.dtos.BookDTO;
import dev.bakr.library_manager.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
    private final BookDTOMapper bookDTOMapper;

    // this annotation also can be removed if it's a constructor injection like this.
    @Autowired
    public BookService(BookRepository bookRepository, BookDTOMapper bookDTOMapper) {
        this.bookRepository = bookRepository;
        // Injects the BookDTOMapper bean, a functional interface implementation used to convert Book entities to DTOs
        this.bookDTOMapper = bookDTOMapper;
    }

    public List<BookDTO> getBooks() {
        return bookRepository.findAll()
                .stream()
                .map(bookDTOMapper).collect(Collectors.toList());
    }

    /* Returns the bookDTO by ID using ResponseEntity (represents the entire HTTP response), which lets us control the
    HTTP status and body. Sends 200 OK if found, or throws an error if not. */
    public ResponseEntity<BookDTO> getBook(Integer id) {
        /* findById returns Optional<Book>, a container that may or may not hold a value (Book). If a Book is found, map
        it to a BookDTO. Otherwise, throw a BookException. */
        var bookDTO = bookRepository.findById(id).map(bookDTOMapper).orElse(null);
        if (bookDTO == null) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return ResponseEntity.notFound().build();
        }
//        return new ResponseEntity<>(bookDTO, HttpStatus.OK);
        return ResponseEntity.ok(bookDTO);
    }
}
