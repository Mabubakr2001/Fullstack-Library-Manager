package dev.bakr.library_manager.responseDtos;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record PublisherDtoResponse(Integer id, String name, String website) {
}
