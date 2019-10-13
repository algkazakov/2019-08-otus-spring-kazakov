package ru.otus.spring.dao;

import ru.otus.spring.domain.Book;
import ru.otus.spring.dto.BookDTO;

import java.util.List;

public interface BookDao {

    int count();

    void insert(BookDTO book);

    void update(BookDTO book);

    BookDTO getById(long id);

    List<BookDTO> getAll();

    List<BookDTO> getAllFull();

    void deleteById(long id);

    long getNextId();
}
