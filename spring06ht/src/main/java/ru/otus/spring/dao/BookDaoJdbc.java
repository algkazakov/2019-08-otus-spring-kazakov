package ru.otus.spring.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.dto.BooksAuthorsDto;
import ru.otus.spring.dto.BooksGenresDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class BookDaoJdbc implements BookDao {

    private final NamedParameterJdbcOperations jdbc;

    public BookDaoJdbc(NamedParameterJdbcOperations jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public int count() {
        return jdbc.getJdbcOperations().queryForObject("select count(*) from BOOKS", Integer.class);
    }

    @Override
    public void insert(Book book) {
        Map<String, Object> params = new HashMap<>();
        params.put("name", book.getName());
        jdbc.update("insert into BOOKS (`NAME`) values (:name)", params);
        long nextBookId = jdbc.getJdbcOperations().queryForObject("select  SCOPE_IDENTITY()", Long.class);
        Book insertedBook = new Book(nextBookId, book.getName());
        insertedBook.setAuthors(book.getAuthors());
        insertedBook.setGenres(book.getGenres());
        setAuthorsAndGenres(insertedBook);
    }

    private void setAuthorsAndGenres(Book book) {
        Map<String, Object> params;
        if (!book.getAuthors().isEmpty()) {
            Map[] paramaterArray = new Map[book.getAuthors().size()];
            for (int i = 0; i < book.getAuthors().size(); i++) {
                params = new HashMap<>();
                params.put("bookid", book.getId());
                params.put("authorid", ((Author) book.getAuthors().toArray()[i]).getId());
                paramaterArray[i] = params;
            }
            jdbc.batchUpdate("insert into BOOKS_AUTHORS (BOOKID, AUTHORID) values (:bookid, :authorid)", paramaterArray);
        }
        if (!book.getGenres().isEmpty()) {
            Map[] paramaterArray = new Map[book.getGenres().size()];
            for (int i = 0; i < book.getGenres().size(); i++) {
                params = new HashMap<>();
                params.put("bookid", book.getId());
                params.put("genreid", ( (Genre) book.getGenres().toArray()[i]).getId());
                paramaterArray[i] = params;
            }
            jdbc.batchUpdate("insert into BOOKS_GENRES (BOOKID, GENREID) values (:bookid, :genreid)", paramaterArray);
        }
    }

    @Override
    public void update(Book book) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", book.getId());
        params.put("name", book.getName());
        jdbc.update("update BOOKS set `NAME` = :name where ID = :id", params);
        if (!book.getAuthors().isEmpty()) {
            jdbc.update("delete from BOOKS_AUTHORS where BOOKID = :id", params);
            jdbc.update("delete from BOOKS_GENRES where BOOKID = :id", params);
            setAuthorsAndGenres(book);
        }
    }

    @Override
    public Book getById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        try {
            Book result = jdbc.queryForObject(
                    "select * from BOOKS where ID = :id", params, new BookMapper()
            );
            List<Author> authors = jdbc.query(
                    "select a.ID, a.NAME from BOOKS_AUTHORS ba join AUTHORS a on ba.AUTHORID = a.ID where ba.BOOKID = :id", params, new AuthorMapper()
            );
            result.setAuthors(authors);
            List<Genre> genres = jdbc.query(
                    "select g.ID, g.NAME from BOOKS_GENRES bg join GENRES g on bg.GENREID = g.ID where bg.BOOKID = :id", params, new GenreMapper()
            );
            result.setGenres(genres);
            return result;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Book> getAll() {
        return jdbc.query("select * from BOOKS order by ID", new BookMapper());
    }

    @Override
    public List<Book> getAllFull() {
        List<Book> books = jdbc.query("select ID, NAME from BOOKS order by ID", new BookMapper());
        List<Author> authors = jdbc.query("select distinct ID, NAME from AUTHORS join BOOKS_AUTHORS on AUTHORS.ID = BOOKS_AUTHORS.AUTHORID order by ID", new AuthorMapper());
        List<Genre> genres = jdbc.query("select distinct ID, NAME from GENRES join BOOKS_GENRES on GENRES.ID = BOOKS_GENRES.GENREID order by ID", new GenreMapper());
        List<BooksAuthorsDto> booksAuthors = jdbc.query("select BOOKID, AUTHORID from BOOKS_AUTHORS order by BOOKID", new BooksAuthorsMapper());
        List<BooksGenresDto> booksGenres = jdbc.query("select BOOKID, GENREID from BOOKS_GENRES order by BOOKID", new BooksGenresMapper());
        Map<Long, List<Author>> booksAuthorsMap = listBooksAuthorsToMap(authors, booksAuthors);
        Map<Long, List<Genre>> booksGenresMap = listBooksGenresToMap(genres, booksGenres);
        for (Book book: books) {
            book.setAuthors(booksAuthorsMap.get(book.getId()));
            book.setGenres(booksGenresMap.get(book.getId()));
        }
        return books;
    }

    @Override
    public void deleteById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        jdbc.update("delete from BOOKS where ID = :id", params);
    }

    @Override
    public long getNextId() {
        return jdbc.getJdbcOperations().queryForObject("select max(id) from BOOKS", Long.class) + 1;
    }

    private Map<Long, Author> listAuthorToMap(List<Author> list) {
        Map<Long, Author> result = new HashMap<>();
        for (Author item: list) {
            result.put(item.getId(), item);
        }
        return result;
    }

    private Map<Long, Genre> listGenreToMap(List<Genre> list) {
        Map<Long, Genre> result = new HashMap<>();
        for (Genre item: list) {
            result.put(item.getId(), item);
        }
        return result;
    }

    private Map<Long, List<Author>> listBooksAuthorsToMap(List<Author> authors, List<BooksAuthorsDto> list) {
        Map<Long, List<Author>> result = new HashMap<>();
        Map<Long, Author> authorsMap = listAuthorToMap(authors);
        for (BooksAuthorsDto item: list) {
            if (!result.containsKey(item.getBookId())) {
                result.put(item.getBookId(), new ArrayList<>());
            }
            result.get(item.getBookId()).add(authorsMap.get(item.getAuthorId()));
        }
        return result;
    }

    private Map<Long, List<Genre>> listBooksGenresToMap(List<Genre> genres, List<BooksGenresDto> list) {
        Map<Long, List<Genre>> result = new HashMap<>();
        Map<Long, Genre> genresMap = listGenreToMap(genres);
        for (BooksGenresDto item: list) {
            if (!result.containsKey(item.getBookId())) {
                result.put(item.getBookId(), new ArrayList<>());
            }
            result.get(item.getBookId()).add(genresMap.get(item.getGenreId()));
        }
        return result;
    }

    private static class BookMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            Book result = new Book(id, name);
            return result;
        }
    }

    private static class AuthorMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            Author result = new Author(id, name);
            return result;
        }
    }

    private static class BooksAuthorsMapper implements RowMapper<BooksAuthorsDto> {

        @Override
        public BooksAuthorsDto mapRow(ResultSet resultSet, int i) throws SQLException {
            long bookId = resultSet.getLong("bookid");
            long authorId = resultSet.getLong("authorid");
            BooksAuthorsDto result = new BooksAuthorsDto(bookId, authorId);
            return result;
        }
    }

    private static class GenreMapper implements RowMapper<Genre> {

        @Override
        public Genre mapRow(ResultSet resultSet, int i) throws SQLException {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            Genre result = new Genre(id, name);
            return result;
        }
    }

    private static class BooksGenresMapper implements RowMapper<BooksGenresDto> {

        @Override
        public BooksGenresDto mapRow(ResultSet resultSet, int i) throws SQLException {
            long bookId = resultSet.getLong("bookid");
            long genreId = resultSet.getLong("genreid");
            BooksGenresDto result = new BooksGenresDto(bookId, genreId);
            return result;
        }
    }

    /*private class BooksExtractor implements ResultSetExtractor<List<Book>> {

        public List<Book> extractData(ResultSet rs) throws SQLException, DataAccessException {
            List<Book> list = new ArrayList<Book>();
            Book book = null;
            long currentBookId = 0;
            int i = 1;
            while(rs.next()){
                long bookId = rs.getLong("ID");
                if (currentBookId != bookId) {
                    book = new Book(bookId, rs.getString("NAME"));
                    list.add(book);
                    currentBookId = bookId;
                }
                Long authorID = rs.getLong("authorID");
                String authorName = rs.getString("authorName");
                if (authorID != 0) {
                    book.addAuthor(new Author(authorID, authorName));
                }
                Long genreID = rs.getLong("genreID");
                String genreName = rs.getString("genreName");
                if (genreID != 0) {
                    book.addGenre(new Genre(genreID, genreName));
                }
            }
            return list;
        }
    }*/
}