package dev.bakr.library_manager.responseDtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import dev.bakr.library_manager.enums.BookStatus;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record BookDtoResponse(Integer id,
        String title,
        String isbn,
        String imageLink,
        BookStatus status,
        AuthorDtoResponse author,
        CategoryDtoResponse category,
        PublisherDtoResponse publisher) {
}
