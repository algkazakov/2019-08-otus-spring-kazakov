package ru.otus.spring.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.domain.Genre;

public interface GenreDao extends MongoRepository<Genre, String> {
}
