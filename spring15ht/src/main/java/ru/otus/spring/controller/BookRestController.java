package ru.otus.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.dao.AuthorDao;
import ru.otus.spring.dao.BookDao;
import ru.otus.spring.dao.CommentDao;
import ru.otus.spring.dao.GenreDao;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.util.PageUtil;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
public class BookRestController {

    private final BookDao repository;
    private final AuthorDao repositoryAuthor;
    private final GenreDao repositoryGenre;
    private final CommentDao repositoryComment;

    @Autowired
    public BookRestController(BookDao repository, AuthorDao repositoryAuthor,
            GenreDao repositoryGenre,CommentDao repositoryComment) {
        this.repository = repository;
        this.repositoryAuthor = repositoryAuthor;
        this.repositoryGenre = repositoryGenre;
        this.repositoryComment = repositoryComment;
    }

    @GetMapping("/list")
    public List<Book> getBookList() {
        List<Book> books = repository.findAll();
        return books;
    }

}
