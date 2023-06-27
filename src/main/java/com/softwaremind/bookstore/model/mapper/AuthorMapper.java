package com.softwaremind.bookstore.model.mapper;


import com.softwaremind.bookstore.model.dto.AuthorDTO;
import com.softwaremind.bookstore.model.entity.Author;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
    AuthorMapper INSTANCE = Mappers.getMapper(AuthorMapper.class);

    AuthorDTO toDTO(Author entity);
    List<AuthorDTO> toDTOList(List<Author> entityList);
}
