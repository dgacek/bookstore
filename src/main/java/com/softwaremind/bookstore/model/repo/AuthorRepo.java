package com.softwaremind.bookstore.model.repo;

import com.softwaremind.bookstore.model.entity.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepo extends JpaRepository<Author, Long> {
    Boolean existsByName(String name);
    Page<Author> findAllBySearchStringContaining(String search, Pageable pageable);
}
