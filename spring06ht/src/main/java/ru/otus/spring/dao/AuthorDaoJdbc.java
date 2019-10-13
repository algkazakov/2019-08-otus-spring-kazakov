package ru.otus.spring.dao;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class AuthorDaoJdbc implements AuthorDao {

    private final NamedParameterJdbcOperations jdbc;

    public AuthorDaoJdbc(NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.jdbc = namedParameterJdbcOperations;
    }

    @Override
    public int count() {
        return jdbc.getJdbcOperations().queryForObject("select count(*) from AUTHORS", Integer.class);
    }

    @Override
    public void insert(Author author) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", author.getId());
        params.put("name", author.getName());
        jdbc.update("insert into AUTHORS (ID, `NAME`) values (:id, :name)", params);
    }

    @Override
    public void update(Author author) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", author.getId());
        params.put("name", author.getName());
        jdbc.update("update AUTHORS set `NAME` = :name where ID = :id", params);
    }

    @Override
    public Author getById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        return jdbc.queryForObject(
                "select * from AUTHORS where ID = :id", params, new AuthorMapper()
        );
    }

    @Override
    public List<Author> getListById(List<Long> ids) {
        Map<String, Object> params = Collections.singletonMap("ids", ids);
        return jdbc.query(
                "select * from AUTHORS where ID IN (:ids)", params, new AuthorMapper()
        );
    }

    @Override
    public void deleteById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        jdbc.update("delete from BOOKS_AUTHORS where AUTHORID = :id", params
        );
        jdbc.update("delete from AUTHORS where ID = :id", params
        );
    }

    @Override
    public long getNextId() {
        return jdbc.getJdbcOperations().queryForObject("select max(id) from AUTHORS", Long.class) + 1;
    }

    @Override
    public List<Author> getAll() {
        return jdbc.query("select * from AUTHORS", new AuthorMapper());
    }

    private static class AuthorMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet resultSet, int i) throws SQLException {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            return new Author(id, name);
        }
    }
}
