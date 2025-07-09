package dev.bakr.library_manager.book;

import dev.bakr.library_manager.author.AuthorDTO;
import dev.bakr.library_manager.category.CategoryDTO;
import dev.bakr.library_manager.publisher.PublisherDTO;

public record BookDTO(Integer id,
        String title,
        String isbn,
        String imageLink,
        BookStatus status,
        AuthorDTO author,
        CategoryDTO category,
        PublisherDTO publisher) {
}
