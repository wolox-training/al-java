package wolox.training.controllers;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.ArgumentMatchers.any;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import wolox.training.models.Book;
import wolox.training.repositories.BookRepository;

@RunWith(SpringRunner.class)
@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookRepository mockedBookRepository;

    @MockBean
    private Book theMist;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        theMist = new Book("Terror", "Stephen King", "The Mist", "no value",
            "SOME PUBLISHER", "2000", 123, "143565786", "imageOfBook");
    }

    @Test
    public void givenBook_whenGetBookByAuthor_thenReturnsCorrectBook() throws Exception {
        given(mockedBookRepository.findFirstByAuthor("The Mist")).willReturn(
            java.util.Optional.ofNullable(theMist));

        mockMvc.perform(get("/api/books/author/" + theMist.getTitle()))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$.isbn", is(theMist.getIsbn())));
    }

    @Test
    public void givenNotExistingBook_whenTriesToFindBookByAuthor_thenReturnsCorrectException() throws Exception
    {
        given(mockedBookRepository.findFirstByAuthor("The Mist")).willReturn(
            java.util.Optional.ofNullable(theMist));

        mockMvc
            .perform(get(URI.create(String.format("/api/books/author/%s", "NoExistingBook")))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }

    @Test
    public void givenBook_whenUpdatesIt_thenReturnsBook() throws Exception
    {
        given(mockedBookRepository.findById(theMist.getId())).willReturn(
            java.util.Optional.ofNullable(theMist));

        String newPublisher = "Another Publisher";
        theMist.setPublisher(newPublisher);

        given(mockedBookRepository.save(any())).willReturn(theMist);

        mockMvc.perform(put("/api/books/" + theMist.getId())
            .content(objectMapper.writeValueAsString(theMist))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.publisher", is(theMist.getPublisher())));
    }

    @Test
    public void givenBook_whenDeletesIt_thenReturnsNotContent() throws Exception
    {
        given(mockedBookRepository.findById(theMist.getId())).willReturn(
            java.util.Optional.ofNullable(theMist));

        mockMvc.perform(delete(URI.create(String.format("/api/books/%d", theMist.getId())))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }
    @Test
    public void givenValidBook_whenCreatesIt_thenReturnsBook() throws Exception {
        given(mockedBookRepository.save(any())).willReturn(theMist);

        mockMvc.perform(post(URI.create("/api/books"))
            .content(objectMapper.writeValueAsString(theMist))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated());
    }



}
