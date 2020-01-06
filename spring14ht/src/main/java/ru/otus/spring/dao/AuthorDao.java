package ru.otus.spring.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.domain.Author;

public interface AuthorDao extends MongoRepository<Author, String> {

}
