package ru.otus.spring.dao;

import org.springframework.data.repository.CrudRepository;
import ru.otus.spring.domain.Book;

public interface BookDao extends CrudRepository<Book, Long> {

}
