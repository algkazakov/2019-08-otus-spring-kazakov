package ru.otus.spring.dto;

import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;

import java.util.*;

public class BookDTO {

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    private Book book;

    public Set<Author> getAuthors() {
        return authors;
    }

    public void addAuthor(Author author) {
        authors.add(author);
    }

    public Set<Genre> getGenres() {
        return genres;
    }

    public void addGenre(Genre genre) {
        genres.add(genre);
    }

    private Set<Author> authors = new HashSet<Author>();

    public void setAuthors(List<Author> authors) {
        this.authors.addAll(authors);
    }

    public void setGenres(List<Genre> genres) {
        this.genres.addAll(genres);
    }

    private Set<Genre> genres = new HashSet<Genre>();
}
