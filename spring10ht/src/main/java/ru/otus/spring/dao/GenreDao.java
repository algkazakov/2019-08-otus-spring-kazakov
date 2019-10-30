package ru.otus.spring.dao;

import org.springframework.data.repository.CrudRepository;
import ru.otus.spring.domain.Genre;

public interface GenreDao extends CrudRepository<Genre, Long> {
}
