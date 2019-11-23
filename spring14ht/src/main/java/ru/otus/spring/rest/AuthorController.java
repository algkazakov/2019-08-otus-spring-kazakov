package ru.otus.spring.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.spring.dao.AuthorDao;
import ru.otus.spring.domain.Author;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class AuthorController {

    private final AuthorDao repository;

    @Autowired
    public AuthorController(AuthorDao repository) {
        this.repository = repository;
    }

    @GetMapping("/author")
    public String listAuthor(Model model,
                           @RequestParam("page") Optional<Integer> page,
                           @RequestParam("size") Optional<Integer> size) {
        List<Author> authors = repository.findAll();
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);

        Page<Author> authorPage = findPaginated(PageRequest.of(currentPage - 1, pageSize), authors);

        model.addAttribute("authorPage", authorPage);

        int totalPages = authorPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
            model.addAttribute("pageNumber", currentPage);
        }
        return "listAuthor";
    }

    @GetMapping("/author/new")
    public String newAuthor(Author author) {
        return "addAuthor";
    }

    @PostMapping("/author/add")
    public String addAuthor(Author author,
                          BindingResult result,
                          @RequestParam("page") Optional<Integer> page,
                          @RequestParam("size") Optional<Integer> size, Model model) {
        repository.save(author);
        return listAuthor(model, page, size);
    }

    @GetMapping("/author/edit/{id}")
    public String editAuthor(@PathVariable("id") String id,
                             @RequestParam("page") Optional<Integer> page,
                             @RequestParam("size") Optional<Integer> size,
                             Model model) {
        Author author = repository.findById(id).orElseThrow(NotFoundException::new);
        model.addAttribute("author", author);
        setPageNum(page, size, model);
        return "editAuthor";
    }

    @PostMapping("/author/update/{id}")
    public String updateAuthor(
            @PathVariable("id") String id, Author author,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size,
            Model model
    ) {
        repository.save(author);
        return listAuthor(model, page, size);
    }

    @GetMapping("/author/delete/{id}")
    public String deleteAuthor(@PathVariable("id") String id,
                               @RequestParam("page") Optional<Integer> page,
                               @RequestParam("size") Optional<Integer> size,
                               Model model) {
        Author author = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid author Id:" + id));
        repository.delete(author);
        return listAuthor(model, page, size);
    }

    private void setPageNum(@RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size, Model model) {
        Integer pageNum = page.orElse(1);
        if (pageNum > 1) {
            model.addAttribute("page", pageNum);
        }
        Integer sizeNum = size.orElse(1);
        if (sizeNum > 1) {
            model.addAttribute("size", sizeNum);
        }
    }

    private Page<Author> findPaginated(Pageable pageable, List<Author> authors) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Author> list;

        if (authors.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, authors.size());
            list = authors.subList(startItem, toIndex);
        }

        Page<Author> authorPage
                = new PageImpl<Author>(list, PageRequest.of(currentPage, pageSize), authors.size());

        return authorPage;
    }
}