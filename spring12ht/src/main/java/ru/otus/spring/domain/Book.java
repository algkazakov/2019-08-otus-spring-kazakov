package ru.otus.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "books")
public @Data
class Book {

    @Id
    private String id;
    private String name;
    @DBRef
    private Set<Author> authors = new HashSet<>();
    @DBRef
    private Set<Genre> genres = new HashSet<>();

    public Book(String name, Iterable<Author> authors, Iterable<Genre> genres) {
        this.name = name;
        for (Author author: authors) {
            this.authors.add(author);
        }
        for (Genre genre: genres) {
            this.genres.add(genre);
        }
    }

    public Book(String name, Iterable<Author> authors) {
        this.name = name;
        for (Author author: authors) {
            this.authors.add(author);
        }
    }

    public Book(String id, String name, Iterable<Author> authors, Iterable<Genre> genres) {
        this(name, authors, genres);
        this.id = id;
    }

    /*public void setAuthors(Iterable<Author> authors) {
        for (Author author: authors) {
            this.authors.add(author);
        }
    }

    public void setGenres(Iterable<Genre> genres) {
        for (Genre genre: genres) {
            this.genres.add(genre);
        }
    }*/
}
