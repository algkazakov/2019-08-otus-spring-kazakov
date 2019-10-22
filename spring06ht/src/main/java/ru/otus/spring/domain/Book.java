package ru.otus.spring.domain;

import lombok.Data;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Genre;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public @Data
class Book {

    private final long id;
    private final String name;
    private final Set<Author> authors = new HashSet<Author>();
    private final Set<Genre> genres = new HashSet<Genre>();

    public void addAuthor(Author author) {
        authors.add(author);
    }

    public void addGenre(Genre genre) {
        genres.add(genre);
    }

    public void setAuthors(List<Author> authors) {
        this.authors.addAll(authors);
    }

    public void setAuthors(Set<Author> authors) {
        this.authors.addAll(authors);
    }

    public void setGenres(List<Genre> genres) {
        this.genres.addAll(genres);
    }

    public void setGenres(Set<Genre> genres) {
        this.genres.addAll(genres);
    }

}
