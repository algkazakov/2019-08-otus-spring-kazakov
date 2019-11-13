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
    String enterBookNumber();
    String enterAuthorName();
    String enterAuthorNumber();
    String enterGenreName();
    String enterGenreNumber();
    List<String> enterAuthors();
    List<String> enterGenres();
    String enterComment();
    String enterCommentNumber();
}
