package ru.otus.spring.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class GenreDaoJdbc implements GenreDao {

    private final NamedParameterJdbcOperations jdbc;

    public GenreDaoJdbc(NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.jdbc = namedParameterJdbcOperations;
    }

    @Override
    public int count() {
        return jdbc.getJdbcOperations().queryForObject("select count(*) from GENRES", Integer.class);
    }

    @Override
    public void insert(Genre genre) {
        Map<String, Object> params = Collections.singletonMap("name", genre.getName());
        jdbc.update("insert into GENRES (`NAME`) values (:name)", params);
    }

    @Override
    public void update(Genre genre) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", genre.getId());
        params.put("name", genre.getName());
        jdbc.update("update GENRES set `NAME` = :name where ID = :id", params);
    }

    @Override
    public Genre getById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        try {
            return jdbc.queryForObject(
                    "select * from GENRES where ID = :id", params, new GenreMapper()
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Genre> getListById(List<Long> ids) {
        Map<String, Object> params = Collections.singletonMap("ids", ids);
        return jdbc.query(
                "select * from GENRES where ID IN (:ids)", params, new GenreMapper()
        );
    }

    @Override
    public void deleteById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        jdbc.update("delete from GENRES where ID = :id", params);
    }

    @Override
    public List<Genre> getAll() {
        return jdbc.query("select * from GENRES", new GenreMapper());
    }
}
