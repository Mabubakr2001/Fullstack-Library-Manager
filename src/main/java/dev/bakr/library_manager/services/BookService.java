package dev.bakr.library_manager.services;

import dev.bakr.library_manager.enums.BookStatus;
import dev.bakr.library_manager.exceptions.BookNotFoundException;
import dev.bakr.library_manager.exceptions.InvalidBookStatusException;
import dev.bakr.library_manager.external.googlebooks.GoogleBooksClient;
import dev.bakr.library_manager.helpers.BookStatusValidator;
import dev.bakr.library_manager.mappers.BookMapper;
import dev.bakr.library_manager.repositories.BookRepository;
import dev.bakr.library_manager.requestDtos.BookDtoRequest;
import dev.bakr.library_manager.responseDtos.BookDtoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
    private final AuthorService authorService;
    private final CategoryService categoryService;
    private final PublisherService publisherService;
    private final GoogleBooksClient googleBooksClient;
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    // this annotation also can be removed if it's a constructor injection like this.
    @Autowired
    public BookService(AuthorService authorService,
            CategoryService categoryService,
            PublisherService publisherService,
            GoogleBooksClient googleBooksClient,
            BookRepository bookRepository,
            BookMapper bookMapper) {
        this.authorService = authorService;
        this.categoryService = categoryService;
        this.publisherService = publisherService;
        this.googleBooksClient = googleBooksClient;
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    public List<BookDtoResponse> getBooks() {
        return bookRepository.findAll().stream().map(bookMapper::toDto).collect(Collectors.toList());
    }

    /* Retrieves a book by its ID from the repository. If found, it is mapped to a BookDtoResponse and returned.
    If not found, a BookNotFoundException is thrown, which can be globally handled to return a 404 response. */
    public BookDtoResponse getBook(Integer id) {
        var book = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException("Book with ID " + id + " not found"));
        return bookMapper.toDto(book);
    }

    public BookDtoResponse addBook(BookDtoRequest bookDtoRequest) {
        var givenBookStatus = bookDtoRequest.status();
        boolean validStatus = BookStatusValidator.validateStatus(givenBookStatus);

        if (!validStatus) {
            throw new InvalidBookStatusException("Invalid book status: '" + givenBookStatus + "'. Allowed values are: " + Arrays.toString(
                    BookStatus.values()) + ", and can be lowercase.");
        }

        // 1. Fetch more info based on the publisher name (e.g., from external logic or a placeholder)
        List<String> parsedBookMoreInfo = googleBooksClient.getParsedBookMoreInfo(bookDtoRequest.title(),
                                                                                  List.of("publishedDate", "subtitle")
        );

        // 2. Map the incoming BookDtoRequest to a Book entity using MapStruct
        var newBookEntity = bookMapper.toEntity(bookDtoRequest);

        // 3. Set the calculated publishing date on the new book entity
        newBookEntity.setPublishedOn(LocalDate.parse(parsedBookMoreInfo.getFirst()));

        // 4. Set the fetched subtitle
        newBookEntity.setSubtitle(parsedBookMoreInfo.get(1));

        // 5. Set the Author object on the book (either fetch existing or create new if not found)
        newBookEntity.setAuthor(authorService.findOrCreateAuthor(bookDtoRequest.authorFullName()));

        // 6. Set the Category object on the book (same logic: fetch or create)
        newBookEntity.setCategory(categoryService.findOrCreateCategory(bookDtoRequest.categoryName()));

        // 7. Set the Publisher object on the book (fetch or create based on name)
        newBookEntity.setPublisher(publisherService.findOrCreatePublisher(bookDtoRequest.publisherName()));

        // 8. Persist the fully prepared Book entity into the database
        var savedBook = bookRepository.save(newBookEntity);

        return bookMapper.toDto(savedBook);
    }
}
