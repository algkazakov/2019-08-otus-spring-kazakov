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

    Author ChristianBauer, GavinKing, GaryGregory, CraigWalls, DavidGriffits, DonGriffits;
    Genre JavaDevelopment, SpringDevelopment, MobileDevelopment;
    Book JavaPersistenceWithHibernate, SpringInAction, HeadFirst;

    @ChangeSet(order = "000", id = "dropDB", author = "akazakov", runAlways = true)
    public void dropDB(MongoDatabase database){
        database.drop();
    }

    @ChangeSet(order = "001", id = "initAuthors", author = "akazakov", runAlways = true)
    public void initAuthors(MongoTemplate template){
        ChristianBauer = template.save(new Author("Christian Bauer"));
        GavinKing = template.save(new Author("Gavin King"));
        GaryGregory = template.save(new Author("Gary Gregory"));
        CraigWalls = template.save(new Author("Craig Walls"));
        DavidGriffits = template.save(new Author("Дэвид Гриффитс"));
        DonGriffits = template.save(new Author("Дон Гриффитс"));
    }

    @ChangeSet(order = "002", id = "initGenres", author = "akazakov", runAlways = true)
    public void initGenres(MongoTemplate template){
        JavaDevelopment = template.save(new Genre("Java development"));
        SpringDevelopment = template.save(new Genre("Spring development"));
        MobileDevelopment = template.save(new Genre("Mobile development"));
    }

    @ChangeSet(order = "003", id = "initBooks", author = "akazakov", runAlways = true)
    public void initBooks(MongoTemplate template){
        List<Author> authors = Arrays.asList(new Author[]{ChristianBauer, GavinKing, GaryGregory});
        List<Genre> genres = Arrays.asList(new Genre[]{JavaDevelopment});
        JavaPersistenceWithHibernate = template.save(new Book("Java Persistence with Hibernate", authors, genres));
        authors = Arrays.asList(new Author[]{CraigWalls});
        genres = Arrays.asList(new Genre[]{SpringDevelopment});
        SpringInAction = template.save(new Book("Spring in Action", authors, genres));
        authors = Arrays.asList(new Author[]{DavidGriffits, DonGriffits});
        genres = Arrays.asList(new Genre[]{JavaDevelopment, MobileDevelopment});
        HeadFirst = template.save(new Book("Head First. Программирование для Android", authors, genres));
    }

    @ChangeSet(order = "004", id = "initComments", author = "akazakov", runAlways = true)
    public void initComments(MongoTemplate template){
        template.save(new Comment("Cool book", JavaPersistenceWithHibernate));
        template.save(new Comment("So-so", JavaPersistenceWithHibernate));
    }
}
