package dev.bakr.library_manager.requestDtos;

import dev.bakr.library_manager.enums.BookStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

// It includes validation annotations (constraints) to ensure all required fields are provided and correctly formatted.
// Spring will automatically validate this data before it reaches the controller logic.
public record BookDtoRequest(@NotBlank(message = "Title is required") String title,
        @NotBlank(message = "ISBN is required")
        String isbn,

        String ImageLink,

        @NotNull(message = "Pages count is required")
        @Min(value = 1, message = "Pages count must be at least 1")
        Integer pagesCount,

        @NotNull(message = "Status is required")
        BookStatus status,

        @NotBlank(message = "Author name is required")
        String authorName,

        @NotBlank(message = "Category name is required")
        String categoryName,

        @NotBlank(message = "Publisher name is required")
        String publisherName) {
}
