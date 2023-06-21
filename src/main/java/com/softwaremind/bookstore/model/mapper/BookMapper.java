package com.softwaremind.bookstore.model.mapper;

import com.softwaremind.bookstore.model.dto.BookDTO;
import com.softwaremind.bookstore.model.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapper {
    static BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    BookDTO toDTO(Book entity);
    List<BookDTO> toDTOList(List<Book> entityList);
}
