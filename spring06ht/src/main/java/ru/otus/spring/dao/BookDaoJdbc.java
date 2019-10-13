package ru.otus.spring.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.dto.BookDTO;

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
    public void insert(BookDTO book) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", book.getBook().getId());
        params.put("name", book.getBook().getName());
        jdbc.update("insert into BOOKS (ID, `NAME`) values (:id, :name)", params);
        setAuthorsAndGenres(book);
    }

    private void setAuthorsAndGenres(BookDTO book) {
        Map<String, Object> params;
        if (!book.getAuthors().isEmpty()) {
            Map[] paramaterArray = new Map[book.getAuthors().size()];
            for (int i = 0; i < book.getAuthors().size(); i++) {
                params = new HashMap<>();
                params.put("bookid", book.getBook().getId());
                params.put("authorid", ((Author) book.getAuthors().toArray()[i]).getId());
                paramaterArray[i] = params;
            }
            jdbc.batchUpdate("insert into BOOKS_AUTHORS (BOOKID, AUTHORID) values (:bookid, :authorid)", paramaterArray);
        }
        if (!book.getGenres().isEmpty()) {
            Map[] paramaterArray = new Map[book.getGenres().size()];
            for (int i = 0; i < book.getGenres().size(); i++) {
                params = new HashMap<>();
                params.put("bookid", book.getBook().getId());
                params.put("genreid", ( (Genre) book.getGenres().toArray()[i]).getId());
                paramaterArray[i] = params;
            }
            jdbc.batchUpdate("insert into BOOKS_GENRES (BOOKID, GENREID) values (:bookid, :genreid)", paramaterArray);
        }
    }

    @Override
    public void update(BookDTO book) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", book.getBook().getId());
        params.put("name", book.getBook().getName());
        jdbc.update("update BOOKS set `NAME` = :name where ID = :id", params);
        if (!book.getAuthors().isEmpty()) {
            jdbc.update("delete from BOOKS_AUTHORS where BOOKID = :id", params);
            jdbc.update("delete from BOOKS_GENRES where BOOKID = :id", params);
            setAuthorsAndGenres(book);
        }
    }

    @Override
    public BookDTO getById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        BookDTO result = jdbc.queryForObject(
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
    }

    @Override
    public List<BookDTO> getAll() {
        return jdbc.query("select * from BOOKS order by ID", new BookMapper());
    }

    @Override
    public List<BookDTO> getAllFull() {
        return jdbc.query("select b.ID, b.NAME, a.ID as authorID, a.NAME as authorName, g.ID as genreID, g.NAME as genreName " +
                "from BOOKS b left join BOOKS_AUTHORS ba on b.ID = ba.BOOKID " +
                "left join AUTHORS a on  ba.AUTHORID = a.ID left join BOOKS_GENRES gb on b.ID = gb.BOOKID " +
                "left join GENRES g on gb.GENREID = g.ID", new BooksExtractor());
    }

    @Override
    public void deleteById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        jdbc.update("delete from BOOKS_AUTHORS where BOOKID = :id", params);
        jdbc.update("delete from BOOKS_GENRES where BOOKID = :id", params);
        jdbc.update("delete from BOOKS where ID = :id", params);
    }

    @Override
    public long getNextId() {
        return jdbc.getJdbcOperations().queryForObject("select max(id) from BOOKS", Long.class) + 1;
    }

    private static class BookMapper implements RowMapper<BookDTO> {

        @Override
        public BookDTO mapRow(ResultSet resultSet, int i) throws SQLException {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            BookDTO result = new BookDTO();
            result.setBook(new Book(id, name));
            return result;
        }
    }

    private static class AuthorMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet resultSet, int i) throws SQLException {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            Author result = new Author(id, name);
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

    private class BooksExtractor implements ResultSetExtractor<List<BookDTO>> {

        public List<BookDTO> extractData(ResultSet rs) throws SQLException, DataAccessException {
            List<BookDTO> list = new ArrayList<BookDTO>();
            BookDTO bookDTO = null;
            long currentBookId = 0;
            int i = 1;
            while(rs.next()){
                long bookId = rs.getLong("ID");
                if (currentBookId != bookId) {
                    bookDTO = new BookDTO();
                    bookDTO.setBook(new Book(bookId, rs.getString("NAME")));
                    list.add(bookDTO);
                    currentBookId = bookId;
                }
                Long authorID = rs.getLong("authorID");
                String authorName = rs.getString("authorName");
                if (authorID != 0) {
                    bookDTO.addAuthor(new Author(authorID, authorName));
                }
                Long genreID = rs.getLong("genreID");
                String genreName = rs.getString("genreName");
                if (genreID != 0) {
                    bookDTO.addGenre(new Genre(genreID, genreName));
                }
            }
            return list;
        }
    }

    private class BookExtractor implements ResultSetExtractor<BookDTO> {

        public BookDTO extractData(ResultSet rs) throws SQLException, DataAccessException {
            BookDTO bookDTO = new BookDTO();
            while(rs.next()){
                if (bookDTO.getBook() == null) {
                    bookDTO.setBook(new Book(rs.getLong("ID"), rs.getString("NAME")));
                }
                Long authorID = rs.getLong("authorID");
                if (authorID != null) {
                    bookDTO.addAuthor(new Author(authorID, rs.getString("authorName")));
                }
                Long genreID = rs.getLong("genreID");
                if (genreID != null) {
                    bookDTO.addGenre(new Genre(genreID, rs.getString("genreName")));
                }
            }
            return bookDTO;
        }
    }
}
