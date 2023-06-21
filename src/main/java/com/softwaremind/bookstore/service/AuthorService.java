package com.softwaremind.bookstore.service;

import com.softwaremind.bookstore.model.dto.AuthorDTO;
import com.softwaremind.bookstore.model.mapper.AuthorMapper;
import com.softwaremind.bookstore.model.repo.AuthorRepo;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthorService {

    private AuthorRepo authorRepo;

    public Page<AuthorDTO> getAll(Pageable pageable) {
        return authorRepo.findAll(pageable).map(AuthorMapper.INSTANCE::toDTO);
    }


}
