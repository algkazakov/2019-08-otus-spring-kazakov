package ru.otus.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @ManyToMany(targetEntity = Author.class, fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinTable(
            name = "BOOKS_AUTHORS",
            joinColumns = {@JoinColumn(name = "BOOKID")},
            inverseJoinColumns = {@JoinColumn(name = "AUTHORID")}
    )
    private final Set<Author> authors = new HashSet<>();

    @ManyToMany(targetEntity = Genre.class, fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinTable(
            name = "BOOKS_GENRES",
            joinColumns = {@JoinColumn(name = "BOOKID")},
            inverseJoinColumns = {@JoinColumn(name = "GENREID")}
    )
    private final Set<Genre> genres = new HashSet<>();

    @OneToMany(targetEntity = Comment.class, fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "BOOKID")
    @OrderBy("id ASC")
    private final Set<Comment> comments = new HashSet<>();

    public void setAuthors(List<Author> authors) {
        this.authors.addAll(authors);
    }

    public void setGenres(List<Genre> genres) {
        this.genres.addAll(genres);
    }
}
