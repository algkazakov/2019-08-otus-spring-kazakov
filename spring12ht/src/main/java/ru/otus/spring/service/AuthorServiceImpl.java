package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.dao.AuthorDao;
import ru.otus.spring.domain.Author;

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
    public void loadAndPrintAuthorList() {
        Iterable<Author> list = daoAuthor.findAll();
        consoleService.printAuthorList(list);
    }

    @Override
    public void add() {
        String authorName = consoleService.enterAuthorName();
        daoAuthor.save(new Author(authorName));
    }

    @Override
    public void edit() {
        Iterable<Author> list = daoAuthor.findAll();
        consoleService.printAuthorList(list);
        String authorId = consoleService.enterAuthorNumber();
        String authorName = consoleService.enterAuthorName();
        daoAuthor.save(new Author(authorId, authorName));
    }

    @Override
    public void remove() {
        loadAndPrintAuthorList();
        String authorId = consoleService.enterAuthorNumber();
        daoAuthor.deleteById(authorId);
    }
}