package dev.bakr.library_manager.book;

import dev.bakr.library_manager.author.AuthorDTO;
import dev.bakr.library_manager.category.CategoryDTO;
import dev.bakr.library_manager.publisher.PublisherDTO;
import org.springframework.stereotype.Service;

import java.util.function.Function;

/* Implements the Function interface (interface with only one abstract method) to
enable usage in stream operations and controller responses where a simplified,
structured version of Book is needed. */
@Service
public class BookDTOMapper implements Function<Book, BookDTO> {
    @Override
    public BookDTO apply(Book book) {
        return new BookDTO(
                book.getId(),
                book.getTitle(),
                book.getIsbn(),
                book.getImageLink(),
                book.getStatus(),
                new AuthorDTO(
                        book.getAuthor().getId(),
                        book.getAuthor().getFullName()
                ),
                new CategoryDTO(
                        book.getCategory().getId(),
                        book.getCategory().getName()
                ),
                new PublisherDTO(
                        book.getPublisher().getId(),
                        book.getPublisher().getName()
                )
        );
    }
}
