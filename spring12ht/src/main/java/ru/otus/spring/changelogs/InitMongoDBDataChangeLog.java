package ru.otus.spring.changelogs;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.domain.Genre;

import java.util.Arrays;
import java.util.List;

@ChangeLog(order = "001")
public class InitMongoDBDataChangeLog {

    private Author authorChristianBauer;
    private Author authorGavinKing;
    private Author authorGaryGregory;
    private Author authorCraigWalls;
    private Author authorDavidGriffits;
    private Author authorDonGriffits;

    private Genre genreJavaDevelopment;
    private Genre genreSpringDevelopment;
    private Genre genreMobileDevelopment;

    private Book bookJavaPersistenceWithHibernate;
    private Book bookSpringInAction;
    private Book bookHeadFirst;

    @ChangeSet(order = "000", id = "dropDB", author = "akazakov", runAlways = true)
    public void dropDB(MongoDatabase database){
        database.drop();
    }

    @ChangeSet(order = "001", id = "initAuthors", author = "akazakov", runAlways = true)
    public void initAuthors(MongoTemplate template){
        authorChristianBauer = template.save(new Author("Christian Bauer"));
        authorGavinKing = template.save(new Author("Gavin King"));
        authorGaryGregory = template.save(new Author("Gary Gregory"));
        authorCraigWalls = template.save(new Author("Craig Walls"));
        authorDavidGriffits = template.save(new Author("Дэвид Гриффитс"));
        authorDonGriffits = template.save(new Author("Дон Гриффитс"));
    }

    @ChangeSet(order = "002", id = "initGenres", author = "akazakov", runAlways = true)
    public void initGenres(MongoTemplate template){
        genreJavaDevelopment = template.save(new Genre("Java development"));
        genreSpringDevelopment = template.save(new Genre("Spring development"));
        genreMobileDevelopment = template.save(new Genre("Mobile development"));
    }

    @ChangeSet(order = "003", id = "initBooks", author = "akazakov", runAlways = true)
    public void initBooks(MongoTemplate template){
        List<Author> authors = Arrays.asList(authorChristianBauer, authorGavinKing, authorGaryGregory);
        List<Genre> genres = Arrays.asList(genreJavaDevelopment);
        bookJavaPersistenceWithHibernate = template.save(new Book("Java Persistence with Hibernate", authors, genres));
        authors = Arrays.asList(authorCraigWalls);
        genres = Arrays.asList(genreSpringDevelopment);
        bookSpringInAction = template.save(new Book("Spring in Action", authors, genres));
        authors = Arrays.asList(authorDavidGriffits, authorDonGriffits);
        genres = Arrays.asList(genreJavaDevelopment, genreMobileDevelopment);
        bookHeadFirst = template.save(new Book("Head First. Программирование для Android", authors, genres));
    }

    @ChangeSet(order = "004", id = "initComments", author = "akazakov", runAlways = true)
    public void initComments(MongoTemplate template){
        template.save(new Comment("Cool book", bookJavaPersistenceWithHibernate));
        template.save(new Comment("So-so", bookJavaPersistenceWithHibernate));
    }
}
