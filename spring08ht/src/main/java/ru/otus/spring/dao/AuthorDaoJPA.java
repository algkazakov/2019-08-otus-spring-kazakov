package ru.otus.spring.dao;

import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Author;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class AuthorDaoJPA implements AuthorDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public long count() {
        TypedQuery<Long> result = em.createQuery("select count(a) from Author a", Long.class);
        return result.getSingleResult();
    }

    @Override
    public void insert(Author author) {
        em.persist(author);
    }

    @Override
    public void update(Author author) {
        em.merge(author);
    }

    @Override
    public Author getById(long id) {
       return em.find(Author.class, id);
    }

    @Override
    public List<Author> getListById(List<Long> ids) {
        TypedQuery<Author> result = em.createQuery("select a from Author a where a.id IN (:ids)", Author.class);
        result.setParameter("ids", ids);
        return result.getResultList();
    }

    @Override
    public void deleteById(long id) {
        em.remove(getById(id));
    }

    @Override
    public List<Author> getAll() {
        TypedQuery<Author> query = em.createQuery(
                "select a from Author a",
                Author.class);
        return query.getResultList();
    }

}
