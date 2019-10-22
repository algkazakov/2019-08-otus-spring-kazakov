package ru.otus.spring.dto;

import lombok.Data;

public @Data
class BooksGenresDto {

    private final long bookId;
    private final long genreId;
}
