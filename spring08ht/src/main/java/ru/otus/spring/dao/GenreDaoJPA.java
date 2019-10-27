package ru.otus.spring.dao;

import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Genre;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class GenreDaoJPA implements GenreDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public long count() {
        TypedQuery<Long> result = em.createQuery("select count(g) from Genre g", Long.class);
        return result.getSingleResult();
    }

    @Override
    public void insert(Genre genre) {
        em.persist(genre);
    }

    @Override
    public void update(Genre genre) {
        em.merge(genre);
    }

    @Override
    public Genre getById(long id) {
        return em.find(Genre.class, id);
    }

    @Override
    public List<Genre> getListById(List<Long> ids) {
        TypedQuery<Genre> result = em.createQuery("select g from Genre g where g.id IN (:ids)", Genre.class);
        result.setParameter("ids", ids);
        return result.getResultList();
    }

    @Override
    public void deleteById(long id) {
        em.remove(getById(id));
    }

    @Override
    public List<Genre> getAll() {
        TypedQuery<Genre> query = em.createQuery(
                "select g from Genre g",
                Genre.class);
        return query.getResultList();
    }
}
