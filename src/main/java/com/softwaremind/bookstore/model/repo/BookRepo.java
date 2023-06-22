package com.softwaremind.bookstore.model.repo;

import com.softwaremind.bookstore.model.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepo extends JpaRepository<Book, Long> {
    Boolean existsByIsbn(String isbn);
}
