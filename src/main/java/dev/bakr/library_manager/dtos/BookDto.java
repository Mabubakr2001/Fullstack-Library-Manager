package dev.bakr.library_manager.dtos;

import dev.bakr.library_manager.enums.BookStatus;

public record BookDto(Integer id,
        String title,
        String isbn,
        String imageLink,
        BookStatus status,
        AuthorDto author,
        CategoryDto category,
        PublisherDto publisher) {
}
