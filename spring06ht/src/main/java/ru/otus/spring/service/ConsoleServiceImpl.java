package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.dto.BookDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
public class ConsoleServiceImpl implements ConsoleService {

    private final Scanner in = new Scanner(System.in);

    @Override
    public void printBook(BookDTO book) {
        System.out.print(book.getBook().getId() + ". ");
        if (!book.getAuthors().isEmpty()) {
            System.out.print(book.getAuthors());
        }
        System.out.print(" " + book.getBook().getName());
        if (!book.getGenres().isEmpty()) {
            System.out.print(" (" + book.getGenres() + ")");
        }
        System.out.print("\n");
    }

    @Override
    public void printBookList(List<BookDTO> list) {
        for (BookDTO book: list) {
            printBook(book);
        }
    }

    @Override
    public void printAuthorList(List<Author> list) {
        for (Author author: list) {
            System.out.println(author.getId() + ". " + author.getName());
        }
    }

    @Override
    public void printGenreList(List<Genre> list) {
        for (Genre genre: list) {
            System.out.println(genre.getId() + ". " + genre.getName());
        }
    }

    @Override
    public String enterBookName() {
        System.out.println("Enter book name:");
        String result = in.nextLine();
        if (result.trim().isEmpty()) {
            System.out.println("Empty book name isn't allowed");
            return enterBookName();
        }
        return result;
    }

    @Override
    public long enterBookNumber() {
        System.out.println("Enter book number:");
        String result = in.nextLine();
        if (result.trim().isEmpty()) {
            System.out.println("Empty book number isn't allowed");
            return enterBookNumber();
        } else {
            return Long.parseLong(result);
        }
    }

    @Override
    public String enterAuthorName() {
        System.out.println("Enter author name:");
        String result = in.nextLine();
        if (result.trim().isEmpty()) {
            System.out.println("Empty author name isn't allowed");
            return enterAuthorName();
        }
        return result;
    }

    @Override
    public long enterAuthorNumber() {
        System.out.println("Enter author number:");
        String result = in.nextLine();
        if (result.trim().isEmpty()) {
            System.out.println("Empty author number isn't allowed");
            return enterAuthorNumber();
        } else {
            return Long.parseLong(result);
        }
    }

    @Override
    public String enterGenreName() {
        System.out.println("Enter genre name:");
        String result = in.nextLine();
        if (result.trim().isEmpty()) {
            System.out.println("Empty genre name isn't allowed");
            return enterGenreName();
        }
        return result;
    }

    @Override
    public long enterGenreNumber() {
        System.out.println("Enter genre number:");
        String result = in.nextLine();
        if (result.trim().isEmpty()) {
            System.out.println("Empty genre number isn't allowed");
            return enterGenreNumber();
        } else {
            return Long.parseLong(result);
        }
    }

    @Override
    public List<Long> enterAuthors(){
        System.out.println("Enter author's numbers (comma separated):");
        String [] authorIds = in.nextLine().split(",");
        try {
            return getLongs(authorIds);
        } catch (NumberFormatException e) {
            System.out.println("You must enter numbers only. Try again.");
            return enterAuthors();
        }
    }

    private List<Long> getLongs(String[] ids) {
        List<Long> result = new ArrayList<>();
        for (String id: ids) {
            result.add(Long.parseLong(id));
        }
        return result;
    }

    @Override
    public List<Long> enterGenres() {
        System.out.println("Enter genre's numbers (comma separated):");
        String [] genreIds = in.nextLine().split(",");
        try {
            return getLongs(genreIds);
        } catch (NumberFormatException e) {
            System.out.println("You must enter numbers only. Try again.");
            return enterGenres();
        }
    }
}