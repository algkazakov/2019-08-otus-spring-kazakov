package ru.otus.spring.dao;

import org.springframework.data.repository.CrudRepository;
import ru.otus.spring.domain.Author;

import java.util.List;

public interface AuthorDao extends CrudRepository<Author, Long> {

}
