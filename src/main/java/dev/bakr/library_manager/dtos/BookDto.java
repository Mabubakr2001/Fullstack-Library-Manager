package dev.bakr.library_manager.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import dev.bakr.library_manager.enums.BookStatus;

import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record BookDto(Integer id,
        String title,
        String isbn,
        String imageLink,
        LocalDate publishedOn,
        BookStatus status,
        AuthorDto author,
        CategoryDto category,
        PublisherDto publisher) {
}
