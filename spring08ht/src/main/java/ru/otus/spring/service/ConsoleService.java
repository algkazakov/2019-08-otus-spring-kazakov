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
    void printBookList(List<Book> list);
    void printBookListLite(List<Book> list);
    void printAuthorList(List<Author> list);
    void printGenreList(List<Genre> list);
    void printCommentList(Set<Comment> list);
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
