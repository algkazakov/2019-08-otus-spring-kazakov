package ru.otus.spring.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.spring.service.BookService;
import ru.otus.spring.service.AuthorService;
import ru.otus.spring.service.GenreService;

@ShellComponent
public class ShellCommands {

    private final BookService bookService;
    private final AuthorService authorService;
    private final GenreService genreService;

    public ShellCommands(BookService bookService, AuthorService authorService, GenreService genreService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.genreService = genreService;
    }

    @ShellMethod(value = "Books list", key = {"list", "lst", "l"})
    public void listBooks() {
        bookService.loadAndPrintBookList();
    }

    @ShellMethod(value = "Add book", key = {"add", "a"})
    public void addBook() {
        bookService.add();
    }

    @ShellMethod(value = "Edit book", key = {"edit", "e"})
    public void editBook() {
        bookService.edit();
    }

    @ShellMethod(value = "Delete book", key = {"del", "d"})
    public void deleteBook() {
        bookService.remove();
    }

    @ShellMethod(value = "Authors list", key = {"listAuthors", "lstAuth", "la"})
    public void listAuthors() {
        authorService.loadAndPrintAuthorList();
    }

    @ShellMethod(value = "Add author", key = {"addAuthor", "aa"})
    public void addAuthor() {
        authorService.add();
    }

    @ShellMethod(value = "Edit author", key = {"editAuthor", "ea"})
    public void editAuthor() {
        authorService.edit();
    }

    @ShellMethod(value = "Delete author", key = {"delAuthor", "da"})
    public void deleteAuthor() {
        authorService.remove();
    }

    @ShellMethod(value = "Genres list", key = {"listGenres", "lstGen", "lg"})
    public void listGenres() {
        genreService.loadAndPrintGenreList();
    }

    @ShellMethod(value = "Add genre", key = {"addGenre", "ag"})
    public void addGenre() {
        genreService.add();
    }

    @ShellMethod(value = "Edit genre", key = {"editGenre", "eg"})
    public void editGenre() {
        genreService.edit();
    }

    @ShellMethod(value = "Delete genre", key = {"delGenre", "dg"})
    public void deleteGenre() {
        genreService.remove();
    }

}
