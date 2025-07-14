package dev.bakr.library_manager.responseDtos;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record BookDtoResponse(Integer id,
        String title,
        String subtitle,
        String isbn,
        String imageLink,
        String status,
        AuthorDtoResponse author,
        CategoryDtoResponse category,
        PublisherDtoResponse publisher) {
}
