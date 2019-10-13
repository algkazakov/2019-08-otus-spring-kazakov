package ru.otus.spring.domain;

public class Author {

    private final long id;
    private final String name;

    public Author(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object author) {
        return this.id == ((Author) author).getId();
    }

    @Override
    public int hashCode() {
        return Long.hashCode(this.id);
    }
}
