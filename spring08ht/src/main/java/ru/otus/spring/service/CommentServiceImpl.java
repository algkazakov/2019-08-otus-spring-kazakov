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
        consoleService.printBookListLite(list);
        long bookId = consoleService.enterBookNumber();
        consoleService.printCommentList(daoComment.getListByBookId(bookId));
    }

    @Override
    public void add() {
        List<Book> list = dao.getAll();
        consoleService.printBookListLite(list);
        long bookId = consoleService.enterBookNumber();
        String commentText = consoleService.enterComment();
        Book book = dao.getById(bookId);
        Comment comment = new Comment(0L, commentText, book);
        daoComment.insert(comment);
    }

    
    @Override
    public void remove() {
        loadAndPrintCommentList();
        long commentId = consoleService.enterCommentNumber();
        daoComment.deleteById(commentId);
    }
}