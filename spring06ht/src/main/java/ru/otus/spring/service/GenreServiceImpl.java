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
public class GenreServiceImpl implements GenreService {

    private final GenreDao daoGenre;    
    private final ConsoleService consoleService;

    public GenreServiceImpl(final GenreDao daoGenre, final ConsoleService consoleService) {
        this.daoGenre = daoGenre;
        this.consoleService = consoleService;
    }

    @Override
    public void list() {
        List<Genre> list = daoGenre.getAll();
        consoleService.printGenreList(list);
    }

    @Override
    public void add() {
        String genreName = consoleService.enterGenreName();
        daoGenre.insert(new Genre(daoGenre.getNextId(), genreName));
    }

    @Override
    public void edit() {
        List<Genre> list = daoGenre.getAll();
        consoleService.printGenreList(list);
        long genreId = consoleService.enterGenreNumber();
        String genreName = consoleService.enterGenreName();
        daoGenre.update(new Genre(genreId, genreName));
    }

    @Override
    public void remove() {
        list();
        long genreId = consoleService.enterGenreNumber();
        daoGenre.deleteById(genreId);
    }
}