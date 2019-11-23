package ru.otus.spring.dao;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import ru.otus.spring.domain.Book;

@RequiredArgsConstructor
public class BookDaoCustomImpl implements BookDaoCustom {

    private final MongoTemplate mongoTemplate;

    @Override
    public void removeAuthorsById(String id) {
        val query = Query.query(Criteria.where("$id").is(new ObjectId(id)));
        val update = new Update().pull("authors", query);
        mongoTemplate.updateMulti(new Query(), update, Book.class);
    }

    @Override
    public void removeGenresById(String id) {
        val query = Query.query(Criteria.where("$id").is(new ObjectId(id)));
        val update = new Update().pull("genres", query);
        mongoTemplate.updateMulti(new Query(), update, Book.class);
    }
}
