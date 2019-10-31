package ru.otus.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "BOOKS")
public @Data
class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "NAME", nullable = false, unique = true)
    private String name;

    @ManyToMany(targetEntity = Author.class, fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(
            name = "BOOKS_AUTHORS",
            joinColumns = {@JoinColumn(name = "BOOKID")},
            inverseJoinColumns = {@JoinColumn(name = "AUTHORID")}
    )
    @Fetch(FetchMode.SUBSELECT)
    private final Set<Author> authors = new HashSet<>();

    @ManyToMany(targetEntity = Genre.class, fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(
            name = "BOOKS_GENRES",
            joinColumns = {@JoinColumn(name = "BOOKID")},
            inverseJoinColumns = {@JoinColumn(name = "GENREID")}
    )
    @Fetch(FetchMode.SUBSELECT)
    private final Set<Genre> genres = new HashSet<>();

    public void setAuthors(List<Author> authors) {
        this.authors.addAll(authors);
    }

    public void setGenres(List<Genre> genres) {
        this.genres.addAll(genres);
    }
}
