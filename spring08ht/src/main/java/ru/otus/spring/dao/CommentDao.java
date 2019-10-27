package ru.otus.spring.dao;

import ru.otus.spring.domain.Comment;

import java.util.List;
import java.util.Set;

public interface CommentDao {

    Comment getById(long id);

    void deleteById(long id);
}
