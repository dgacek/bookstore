package com.softwaremind.bookstore.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.NaturalId;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String title;

    @NaturalId
    @Column(nullable = false, unique = true)
    private String isbn;

    @ManyToOne
    private Author author;

    @Column(nullable = false)
    private String searchString;

    @PrePersist
    @PreUpdate
    public void generateSearchString() {
        this.searchString = (this.title + this.author.getName() + this.isbn).toLowerCase().replace(" ", "");
    }
}
