package ru.otus.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.spring.dao.AuthorDao;
import ru.otus.spring.domain.Author;
import ru.otus.spring.util.PageUtil;

import java.util.List;
import java.util.Optional;

@Controller
public class AuthorController {

    private final AuthorDao repository;

    @Autowired
    public AuthorController(AuthorDao repository) {
        this.repository = repository;
    }

    @GetMapping("/author")
    public String getAuthorListView(Model model,
                                    @RequestParam("page") Optional<Integer> page,
                                    @RequestParam("size") Optional<Integer> size) {
        List<Author> authors = repository.findAll();
        PageUtil.setPageInfoModel(authors, page, size, model);
        return "listAuthor";
    }

    @GetMapping("/author/new")
    public String addNewAuthor(Author author) {
        return "addAuthor";
    }

    @PostMapping("/author/add")
    public String addAuthor(Author author,
                          BindingResult result,
                          @RequestParam("page") Optional<Integer> page,
                          @RequestParam("size") Optional<Integer> size, Model model) {
        repository.save(author);
        return "redirect:/author?page=" + page.orElse(1) + "&size=" + size.orElse(5);
    }

    @GetMapping("/author/edit/{id}")
    public String editAuthor(@PathVariable("id") String id,
                             @RequestParam("page") Optional<Integer> page,
                             @RequestParam("size") Optional<Integer> size,
                             Model model) {
        Author author = repository.findById(id).orElseThrow(NotFoundException::new);
        model.addAttribute("author", author);
        PageUtil.setPageNum(page, size, model);
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
        return "redirect:/author?page=" + page.orElse(1) + "&size=" + size.orElse(5);
    }

    @PostMapping("/author/delete/{id}")
    public String deleteAuthor(@PathVariable("id") String id,
                               @RequestParam("page") Optional<Integer> page,
                               @RequestParam("size") Optional<Integer> size,
                               Model model) {
        repository.deleteById(id);
        return "redirect:/author?page=" + page.orElse(1) + "&size=" + size.orElse(5);
    }
}