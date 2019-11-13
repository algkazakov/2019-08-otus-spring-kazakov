package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.dao.AuthorDao;
import ru.otus.spring.dao.BookDao;
import ru.otus.spring.dao.GenreDao;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.exception.BookNotFoundException;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookDao dao;
    private final AuthorDao daoAuthor;
    private final GenreDao daoGenre;
    private final ConsoleService consoleService;

    public BookServiceImpl(final BookDao dao, final AuthorDao daoAuthor, final GenreDao daoGenre, final ConsoleService consoleService) {
        this.dao = dao;
        this.daoAuthor = daoAuthor;
        this.daoGenre = daoGenre;
        this.consoleService = consoleService;
    }

    @Override
    public void loadAndPrintBookList() {
        Iterable<Book> list = dao.findAll();
        consoleService.printBookList(list);
    }

    @Override
    public void loadAndPrintBookListLite() {
        Iterable<Book> list = dao.findAll();
        consoleService.printBookListLite(list);
    }

    @Override
    public void add() {
        String bookName = consoleService.enterBookName();
        Iterable<Author> list = daoAuthor.findAll();
        consoleService.printAuthorList(list);
        List<String> authorIds = consoleService.enterAuthors();
        Iterable<Author> authors = daoAuthor.findAllById(authorIds);
        Iterable<Genre> lst = daoGenre.findAll();
        consoleService.printGenreList(lst);
        List<String> genreIds = consoleService.enterGenres();
        Iterable<Genre> genres = daoGenre.findAllById(genreIds);
        Book book = new Book(bookName, authors, genres);
        dao.save(book);
    }

    @Override
    public void edit() {
        loadAndPrintBookListLite();
        String bookId = consoleService.enterBookNumber();
        Book book = dao.findById(bookId == null ? "" : bookId).orElseThrow(() -> new BookNotFoundException());
        consoleService.printBook(book);
        String bookName = consoleService.enterBookName();
        Iterable<Author> aList = daoAuthor.findAll();
        consoleService.printAuthorList(aList);
        List<String> authorIds = consoleService.enterAuthors();
        Iterable<Author> authors = daoAuthor.findAllById(authorIds);
        Iterable<Genre> gList = daoGenre.findAll();
        consoleService.printGenreList(gList);
        List<String> genreIds = consoleService.enterGenres();
        Iterable<Genre> genres = daoGenre.findAllById(genreIds);
        book = new Book(book.getId(), bookName, authors, genres);
        dao.save(book);
    }

    @Override
    public void remove() {
        loadAndPrintBookListLite();
        String bookId = consoleService.enterBookNumber();
        dao.deleteById(bookId);
    }
}