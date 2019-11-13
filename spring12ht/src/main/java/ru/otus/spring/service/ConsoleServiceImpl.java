package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.domain.Book;

import java.util.*;

@Service
public class ConsoleServiceImpl implements ConsoleService {

    private final Scanner in = new Scanner(System.in);

    private static Map<String, Long> bookIdMap = new HashMap<>();
    private static Map<Long, String> bookMapId = new HashMap<>();
    private static Map<String, Long> authorIdMap = new HashMap<>();
    private static Map<Long, String> authorMapId = new HashMap<>();
    private static Map<String, Long> genreIdMap = new HashMap<>();
    private static Map<Long, String> genreMapId = new HashMap<>();
    private static Map<String, Long> commentIdMap = new HashMap<>();
    private static Map<Long, String> commentMapId = new HashMap<>();

    @Override
    public void printBook(Book book) {
        System.out.print(bookIdMap.get(book.getId()) + ". ");
        if (!book.getAuthors().isEmpty()) {
            System.out.print(book.getAuthors());
        }
        System.out.print(" " + book.getName());
        if (!book.getGenres().isEmpty()) {
            System.out.print(" (" + book.getGenres() + ")");
        }
        System.out.print("\n");
    }

    @Override
    public void printBookLite(Book book) {
        System.out.print(bookIdMap.get(book.getId()) + ". ");
        System.out.print(" " + book.getName());
        System.out.print("\n");
    }

    @Override
    public void printBookList(Iterable<Book> list) {
        for (Book book: list) {
            setBookIdMap(book);
            printBook(book);
        }
    }

    @Override
    public void printBookListLite(Iterable<Book> list) {
        for (Book book: list) {
            setBookIdMap(book);
            printBookLite(book);
        }
    }

    @Override
    public void printAuthorList(Iterable<Author> list) {
        for (Author author: list) {
            setAuthorIdMap(author);
            System.out.println(authorIdMap.get(author.getId()) + ". " + author.getName());
        }
    }

    @Override
    public void printGenreList(Iterable<Genre> list) {
        for (Genre genre: list) {
            setGenreIdMap(genre);
            System.out.println(genreIdMap.get(genre.getId()) + ". " + genre.getName());
        }
    }

    @Override
    public void printCommentList(List<Comment> list) {
        for (Comment comment: list) {
            setCommentIdMap(comment);
            System.out.println(commentIdMap.get(comment.getId()) + ". " + comment.getText());
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
    public String enterBookNumber() {
        System.out.println("Enter book number:");
        String result = in.nextLine();
        if (result.trim().isEmpty()) {
            System.out.println("Empty book number isn't allowed");
            return enterBookNumber();
        } else {
            return bookMapId.get(Long.parseLong(result));
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
    public String enterAuthorNumber() {
        System.out.println("Enter author number:");
        String result = in.nextLine();
        if (result.trim().isEmpty()) {
            System.out.println("Empty author number isn't allowed");
            return enterAuthorNumber();
        } else {
            return  authorMapId.get(Long.parseLong(result));
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
    public String enterGenreNumber() {
        System.out.println("Enter genre number:");
        String result = in.nextLine();
        if (result.trim().isEmpty()) {
            System.out.println("Empty genre number isn't allowed");
            return enterGenreNumber();
        } else {
            return  genreMapId.get(Long.parseLong(result));
        }
    }

    @Override
    public List<String> enterAuthors(){
        System.out.println("Enter author's numbers (comma separated):");
        String [] authorIds = in.nextLine().split(",");
        try {
            return getStrings(authorIds, authorMapId);
        } catch (NumberFormatException e) {
            System.out.println("You must enter numbers only. Try again.");
            return enterAuthors();
        }
    }

    private List<String> getStrings(String[] ids, Map<Long, String> idMap) {
        List<String> result = new ArrayList<>();
        for (String id: ids) {
            result.add(idMap.get(Long.parseLong(id)));
        }
        return result;
    }

    @Override
    public List<String> enterGenres() {
        System.out.println("Enter genre's numbers (comma separated):");
        String [] genreIds = in.nextLine().split(",");
        try {
            return getStrings(genreIds, genreMapId);
        } catch (NumberFormatException e) {
            System.out.println("You must enter numbers only. Try again.");
            return enterGenres();
        }
    }

    @Override
    public String enterComment() {
        System.out.println("Enter comment:");
        String result = in.nextLine();
        if (result.trim().isEmpty()) {
            System.out.println("Empty comment isn't allowed");
            return enterComment();
        }
        return result;
    }

    @Override
    public String enterCommentNumber() {
        System.out.println("Enter comment number:");
        String result = in.nextLine();
        if (result.trim().isEmpty()) {
            System.out.println("Empty comment number isn't allowed");
            return enterCommentNumber();
        } else {
            return commentMapId.get(Long.parseLong(result));
        }
    }

    private void setBookIdMap(Book book) {
        if (!bookIdMap.containsKey(book.getId())) {
            bookIdMap.put(book.getId(), getNextValue(bookIdMap));
            bookMapId.put(getNextKey(bookMapId), book.getId());
        }
    }

    private void setAuthorIdMap(Author author) {
        if (!authorIdMap.containsKey(author.getId())) {
            authorIdMap.put(author.getId(), getNextValue(authorIdMap));
            authorMapId.put(getNextKey(authorMapId), author.getId());
        }
    }

    private void setGenreIdMap(Genre genre) {
        if (!genreIdMap.containsKey(genre.getId())) {
            genreIdMap.put(genre.getId(), getNextValue(genreIdMap));
            genreMapId.put(getNextKey(genreMapId), genre.getId());
        }
    }

    private void setCommentIdMap(Comment comment) {
        if (!commentIdMap.containsKey(comment.getId())) {
            commentIdMap.put(comment.getId(), getNextValue(commentIdMap));
            commentMapId.put(getNextKey(commentMapId), comment.getId());
        }
    }

    private long getNextValue(Map<String, Long> idMap) {
        return idMap.isEmpty() ? 1L : Collections.max(idMap.values()) + 1;
    }

    private long getNextKey(Map<Long, String> mapId) {
        return mapId.isEmpty() ? 1L : Collections.max(mapId.keySet()) + 1;
    }
}