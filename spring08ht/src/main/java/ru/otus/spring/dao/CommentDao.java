package ru.otus.spring.dao;

import ru.otus.spring.domain.Comment;

import java.util.List;
import java.util.Set;

public interface CommentDao {

    void insert(Comment comment);

    Comment getById(long id);

    List<Comment> getListByBookId(long bookId);

    void deleteById(long id);
}
