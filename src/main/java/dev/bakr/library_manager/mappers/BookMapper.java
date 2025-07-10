package dev.bakr.library_manager.mappers;

import dev.bakr.library_manager.dtos.BookDto;
import dev.bakr.library_manager.entities.Book;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookMapper {
    BookDto toDto(Book book);
}
