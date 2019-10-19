package ru.otus.spring.dto;

import lombok.Data;

public @Data
class BooksAuthorsDto {

    private final long bookId;
    private final long authorId;
}
