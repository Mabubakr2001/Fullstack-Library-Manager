package dev.bakr.library_manager.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record PublisherDto(Integer id, String name, String website) {
}
