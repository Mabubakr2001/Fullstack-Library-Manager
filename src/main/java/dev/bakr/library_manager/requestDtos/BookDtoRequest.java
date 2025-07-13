package dev.bakr.library_manager.requestDtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

// It includes validation annotations (constraints) to ensure all required fields are provided and correctly formatted.
// Spring will automatically validate this data before it reaches the controller logic.
public record BookDtoRequest(@NotBlank(message = "Title is required") String title,
        @NotBlank(message = "ISBN is required")
        @Pattern(regexp = "97[89][0-9]{10}", message = "ISBN must be a 13-digit number starting with 978 or 979")
        String isbn,

        @NotNull(message = "Pages count is required")
        @Min(value = 1, message = "Pages count must be at least 1")
        Integer pagesCount,

        String imageLink,

        @NotNull(message = "Status is required")
        String status,

        @NotBlank(message = "Author full name is required")
        String authorFullName,

        @NotBlank(message = "Category name is required")
        String categoryName,

        @NotBlank(message = "Publisher name is required")
        String publisherName) {
}
