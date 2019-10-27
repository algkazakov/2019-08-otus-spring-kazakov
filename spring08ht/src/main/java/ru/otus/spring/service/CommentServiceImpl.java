package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.dao.BookDao;
import ru.otus.spring.dao.CommentDao;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private final BookDao dao;
    private final CommentDao daoComment;    
    private final ConsoleService consoleService;

    public CommentServiceImpl(final BookDao dao, final CommentDao daoComment, final ConsoleService consoleService) {
        this.dao = dao;
        this.daoComment = daoComment;
        this.consoleService = consoleService;
    }

    @Override
    public void loadAndPrintCommentList() {
        List<Book> list = dao.getAll();
        consoleService.printBookList(list);
        long bookId = consoleService.enterBookNumber();
        Book book = dao.getById(bookId);
        consoleService.printCommentList(book.getComments());
    }

    @Override
    public void add() {
        List<Book> list = dao.getAll();
        consoleService.printBookList(list);
        long bookId = consoleService.enterBookNumber();
        String comment = consoleService.enterComment();
        Book book = dao.getById(bookId);
        book.getComments().add(new Comment(0, comment));
        dao.update(book);
    }

    
    @Override
    public void remove() {
        loadAndPrintCommentList();
        long commentId = consoleService.enterCommentNumber();
        daoComment.deleteById(commentId);
    }
}