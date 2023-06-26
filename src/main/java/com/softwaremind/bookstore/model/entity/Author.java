package com.softwaremind.bookstore.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "author")
    private List<Book> books;

    @Column(nullable = false)
    private String searchString;

    @PrePersist
    @PreUpdate
    private void generateSearchString() {
        this.searchString = this.name.toLowerCase().replace(" ", "");
    }
}
