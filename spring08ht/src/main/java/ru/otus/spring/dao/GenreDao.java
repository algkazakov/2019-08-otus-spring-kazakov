package ru.otus.spring.dao;

import ru.otus.spring.domain.Genre;

import java.util.List;
import java.util.Set;

public interface GenreDao {

    long count();

    List<Genre> getAll();

    void insert(Genre genre);

    void update(Genre genre);

    Genre getById(long id);

    List<Genre> getListById(List<Long> ids);

    void deleteById(long id);
}
