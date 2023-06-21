package com.softwaremind.bookstore.model.repo;

import com.softwaremind.bookstore.model.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepo extends JpaRepository<Author, Long> {
}
