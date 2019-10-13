package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.dao.AuthorDao;
import ru.otus.spring.dao.BookDao;
import ru.otus.spring.dao.GenreDao;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.dto.BookDTO;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorDao daoAuthor;    
    private final ConsoleService consoleService;

    public AuthorServiceImpl(final AuthorDao daoAuthor, final ConsoleService consoleService) {
        this.daoAuthor = daoAuthor;
        this.consoleService = consoleService;
    }

    @Override
    public void list() {
        List<Author> list = daoAuthor.getAll();
        consoleService.printAuthorList(list);
    }

    @Override
    public void add() {
        String authorName = consoleService.enterAuthorName();
        daoAuthor.insert(new Author(daoAuthor.getNextId(), authorName));
    }

    @Override
    public void edit() {
        List<Author> list = daoAuthor.getAll();
        consoleService.printAuthorList(list);
        long authorId = consoleService.enterAuthorNumber();
        String authorName = consoleService.enterAuthorName();
        daoAuthor.update(new Author(authorId, authorName));
    }

    @Override
    public void remove() {
        list();
        long authorId = consoleService.enterAuthorNumber();
        daoAuthor.deleteById(authorId);
    }
}