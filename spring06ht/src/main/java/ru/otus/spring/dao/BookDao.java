package ru.otus.spring.dao;

import ru.otus.spring.domain.Book;

import java.util.List;

public interface BookDao {

    int count();

    void insert(Book book);

    void update(Book book);

    Book getById(long id);

    List<Book> getAll();

    List<Book> getAllFull();

    void deleteById(long id);

    long getNextId();
}
