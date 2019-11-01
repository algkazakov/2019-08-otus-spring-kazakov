package ru.otus.spring.dao;

import org.springframework.data.repository.CrudRepository;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;

import java.util.List;

public interface CommentDao extends CrudRepository<Comment, Long> {

    List<Comment> findByBook(Book book);
}
