package ru.otus.spring.service;

import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.domain.Book;

import java.util.List;
import java.util.Set;

public interface ConsoleService {

    void printBook(Book book);
    void printBookLite(Book book);
    void printBookList(Iterable<Book> list);
    void printBookListLite(Iterable<Book> list);
    void printAuthorList(Iterable<Author> list);
    void printGenreList(Iterable<Genre> list);
    void printCommentList(List<Comment> list);
    String enterBookName();
    long enterBookNumber();
    String enterAuthorName();
    long enterAuthorNumber();
    String enterGenreName();
    long enterGenreNumber();
    List<Long> enterAuthors();
    List<Long> enterGenres();
    String enterComment();
    long enterCommentNumber();
}
