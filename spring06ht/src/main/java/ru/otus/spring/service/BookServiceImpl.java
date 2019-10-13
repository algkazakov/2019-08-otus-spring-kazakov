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
    public void list() {
        List<BookDTO> list = dao.getAllFull();
        consoleService.printBookList(list);
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
        BookDTO book = new BookDTO();
        book.setBook(new Book(dao.getNextId(), bookName));
        book.setAuthors(authors);
        book.setGenres(genres);
        dao.insert(book);
    }

    @Override
    public void edit() {
        List<BookDTO> list = dao.getAll();
        consoleService.printBookList(list);
        long bookId = consoleService.enterBookNumber();
        BookDTO book = dao.getById(bookId);
        consoleService.printBook(book);
        String bookName = consoleService.enterBookName();
        book.setBook(new Book(book.getBook().getId(), bookName));
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
        list();
        long bookId = consoleService.enterBookNumber();
        dao.deleteById(bookId);
    }
}