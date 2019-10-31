package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.dao.AuthorDao;
import ru.otus.spring.dao.BookDao;
import ru.otus.spring.dao.GenreDao;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.domain.Book;

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
        List<Book> list = dao.getAll();
        consoleService.printBookList(list);
    }

    @Override
    public void loadAndPrintBookListLite() {
        List<Book> list = dao.getAll();
        consoleService.printBookListLite(list);
    }

    @Override
    public void add() {
        String bookName = consoleService.enterBookName();
        List<Author> list = daoAuthor.getAll();
        consoleService.printAuthorList(list);
        List<Long> authorIds = consoleService.enterAuthors();
        List<Author> authors = daoAuthor.getListById(authorIds);
        List<Genre> lst = daoGenre.getAll();
        consoleService.printGenreList(lst);
        List<Long> genreIds = consoleService.enterGenres();
        List<Genre> genres = daoGenre.getListById(genreIds);
        Book book = new Book(0, bookName);
        book.setAuthors(authors);
        book.setGenres(genres);
        dao.insert(book);
    }

    @Override
    public void edit() {
        loadAndPrintBookListLite();
        long bookId = consoleService.enterBookNumber();
        Book book = dao.getById(bookId);
        consoleService.printBook(book);
        String bookName = consoleService.enterBookName();
        book = new Book(book.getId(), bookName);
        List<Author> aList = daoAuthor.getAll();
        consoleService.printAuthorList(aList);
        List<Long> authorIds = consoleService.enterAuthors();
        List<Author> authors = daoAuthor.getListById(authorIds);
        List<Genre> gList = daoGenre.getAll();
        consoleService.printGenreList(gList);
        List<Long> genreIds = consoleService.enterGenres();
        List<Genre> genres = daoGenre.getListById(genreIds);
        book.setAuthors(authors);
        book.setGenres(genres);
        dao.update(book);
    }

    @Override
    public void remove() {
        loadAndPrintBookListLite();
        long bookId = consoleService.enterBookNumber();
        dao.deleteById(bookId);
    }
}