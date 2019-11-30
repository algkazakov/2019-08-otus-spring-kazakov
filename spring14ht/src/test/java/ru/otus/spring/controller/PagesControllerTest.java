package ru.otus.spring.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.spring.dao.AuthorDao;
import ru.otus.spring.dao.BookDao;
import ru.otus.spring.dao.CommentDao;
import ru.otus.spring.dao.GenreDao;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AuthorController.class)
@ComponentScan({"ru.otus.spring.controller"})
public class PagesControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AuthorDao authorRepository;
    @MockBean
    private BookDao bookRepository;
    @MockBean
    private GenreDao genreRepository;
    @MockBean
    private CommentDao commentRepository;

    @Test
    public void testGetListAuthors() throws Exception {
        mockMvc.perform(get("/author"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetListBooks() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetListGenres() throws Exception {
        mockMvc.perform(get("/genre"))
                .andExpect(status().isOk());
    }

    @Test
    public void testAddBook() throws Exception {
        mockMvc.perform(post("/add"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    public void testEditBookFail() throws Exception {
        mockMvc.perform(get("/edit"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testUpdateBookFail() throws Exception {
        mockMvc.perform(post("/update"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testAddAuthorFail() throws Exception {
        mockMvc.perform(get("/addAuthor"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testGetListGenresFail() throws Exception {
        mockMvc.perform(get("/genres"))
                .andExpect(status().is4xxClientError());
    }
}
