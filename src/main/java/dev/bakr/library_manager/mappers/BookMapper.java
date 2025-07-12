package dev.bakr.library_manager.mappers;

import dev.bakr.library_manager.entities.Book;
import dev.bakr.library_manager.requestDtos.BookDtoRequest;
import dev.bakr.library_manager.responseDtos.BookDtoResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookMapper {
    BookDtoResponse toDto(Book book);

    Book toEntity(BookDtoRequest bookDtoRequest);
}
