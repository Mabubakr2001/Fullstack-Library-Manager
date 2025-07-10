package dev.bakr.library_manager.dtos;

import dev.bakr.library_manager.BookStatus;

public record BookDTO(Integer id,
        String title,
        String isbn,
        String imageLink,
        BookStatus status,
        AuthorDTO author,
        CategoryDTO category,
        PublisherDTO publisher) {
}
