package ru.otus.spring.dao;

import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Comment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class CommentDaoJPA implements CommentDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Comment getById(long id) {
        return em.find(Comment.class, id);
    }

    @Override
    public void deleteById(long id) {
        em.remove(getById(id));
    }
    
}
