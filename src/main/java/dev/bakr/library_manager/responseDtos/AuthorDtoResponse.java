package dev.bakr.library_manager.responseDtos;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDate;

/* tell Jackson (a popular Java library used to convert Java objects to JSON and JSON to Java objects
— a process known as serialization and deserialization.) to ignore any null values */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record AuthorDtoResponse(Integer id, String fullName, LocalDate birthDate) {
}
