package ru.otus.spring.service;

import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.domain.Book;

import java.util.List;

public interface ConsoleService {

    void printBook(Book book);
    void printBookList(List<Book> list);
    void printAuthorList(List<Author> list);
    void printGenreList(List<Genre> list);
    String enterBookName();
    long enterBookNumber();
    String enterAuthorName();
    long enterAuthorNumber();
    String enterGenreName();
    long enterGenreNumber();
    List<Long> enterAuthors();
    List<Long> enterGenres();
}
