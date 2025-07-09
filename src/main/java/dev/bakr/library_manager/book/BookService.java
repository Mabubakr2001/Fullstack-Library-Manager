package dev.bakr.library_manager.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/* This class is now a Spring Bean (In Spring, a bean is any class that is managed by
the Spring IoC container — i.e., it’s created, configured, injected, and destroyed by
Spring, not by you.) Because of the @Service annotation. It will be instantiated and
managed automatically by the Spring IoC container (ApplicationContext), and can be
injected into other components (like controllers) as a dependency. */
@Service
public class BookService {
    private final BookRepository bookRepository;

    // this annotation also can be removed if it's a constructor injection like this.
    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getBooks() {
        return bookRepository.findAll();
    }
}
