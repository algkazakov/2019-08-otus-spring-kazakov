package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.dao.GenreDao;
import ru.otus.spring.domain.Genre;

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
    public void loadAndPrintGenreList() {
        Iterable<Genre> list = daoGenre.findAll();
        consoleService.printGenreList(list);
    }

    @Override
    public void add() {
        String genreName = consoleService.enterGenreName();
        daoGenre.save(new Genre(0, genreName));
    }

    @Override
    public void edit() {
        Iterable<Genre> list = daoGenre.findAll();
        consoleService.printGenreList(list);
        long genreId = consoleService.enterGenreNumber();
        String genreName = consoleService.enterGenreName();
        daoGenre.save(new Genre(genreId, genreName));
    }

    @Override
    public void remove() {
        loadAndPrintGenreList();
        long genreId = consoleService.enterGenreNumber();
        daoGenre.deleteById(genreId);
    }
}