package ru.otus.spring.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Book;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class BookDaoJPA implements BookDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public long count() {
        TypedQuery<Long> result = em.createQuery("select count(b) from Book b", Long.class);
        return result.getSingleResult();
    }

    @Override
    public void insert(Book book) {
        em.persist(book);
    }

    @Override
    public void update(Book book) {
        em.merge(book);
    }

    @Override
    public Book getById(long id) {
        try {
            TypedQuery<Book> query = em.createQuery(
                   "select b from Book b where b.id = :id",
                    Book.class);
            query.setParameter("id", id);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Book> getAll() {
        TypedQuery<Book> query = em.createQuery(
                    "select b from Book b order by b.id",
                    Book.class);
        return query.getResultList();
    }

    @Override
    public void deleteById(long id) {
        em.remove(getById(id));
    }
}