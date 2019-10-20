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
        List<Author> list = daoAuthor.getAll();
        consoleService.printAuthorList(list);
    }

    @Override
    public void add() {
        String authorName = consoleService.enterAuthorName();
        daoAuthor.insert(new Author(0, authorName));
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
        loadAndPrintAuthorList();
        long authorId = consoleService.enterAuthorNumber();
        daoAuthor.deleteById(authorId);
    }
}