package ru.otus.spring.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.domain.Book;

public interface BookDao extends MongoRepository<Book, String>, BookDaoCustom {

}
