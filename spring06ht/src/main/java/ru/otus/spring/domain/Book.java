package ru.otus.spring.domain;

import java.util.ArrayList;
import java.util.List;

public class Book {

    private final long id;
    private final String name;

    public Book(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
