package wolox.training.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import wolox.training.models.Book;
import wolox.training.models.User;
import wolox.training.repositories.BookRepository;
import wolox.training.repositories.UserRepository;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository mockedUserRepo;

    @MockBean
    private BookRepository mockedBookRepo;

    @Autowired
    private ObjectMapper objectMapper;

    private User javier;
    private User marihe;
    private User robertV;
    Book theMist;

    @BeforeEach
    void setUp(){
        javier = new User("JaviMo", "Javier Moreno", LocalDate.parse("1980-10-08"));
        marihe = new User("MariHe", "Maria Haize", LocalDate.parse("1990-11-04"));
        robertV = new User("RobertVv", "Robert Velvet", LocalDate.parse("1975-04-11"));
        theMist = new Book("Terror", "Stephen King", "The Mist", "no value",
            "SOME PUBLISHER", "2000", 123, "143565786", "imageOfBook");

    }

    List<User> allUsers = new ArrayList<User>();

    @Test
    public void givenUsers_whenGetUsers_thenReturnJsonArray() throws Exception {
        allUsers.add(javier);
        given(mockedUserRepo.findAll()).willReturn(allUsers);

        mockMvc.perform(get("/api/users/all"))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].username", is(javier.getUsername() )));
    }

    @Test
    public void givenUser_whenGetUserById_thenReturnJsonArrayWithCorrectUser() throws Exception {
        given(mockedUserRepo.findById(marihe.getId())).willReturn(
            java.util.Optional.ofNullable(marihe));

        mockMvc.perform(get("/api/users/" + marihe.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$.username", is(marihe.getUsername() )));
    }

    @Test
    public void givenUser_whenGetUserByUsername_thenRaiseNotFoundException() throws Exception {
        given(mockedUserRepo.findById(marihe.getId())).willReturn(
            java.util.Optional.ofNullable(marihe));

        mockMvc.perform(get("/api/users/" + -1))
            .andExpect(status().isNotFound());
    }

    @Test
    public void givenParams_whenCreatesAnUser_thenReturnsCreatedUser() throws Exception {
        given(mockedUserRepo.save(any())).willReturn(robertV);
        mockMvc.perform(post("/api/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(robertV)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath( "$.username", is( robertV.getUsername())));
    }

    @Test
    public void givenParams_whenUpdatesAnUser_thenReturnsUpdatedUser() throws Exception {
        given(mockedUserRepo.findById(robertV.getId())).willReturn(
            java.util.Optional.ofNullable(robertV));
        given(mockedUserRepo.save(any())).willReturn(robertV);

        mockMvc.perform(put("/api/users/" + robertV.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(robertV)))
                .andExpect(status().isOk())
                .andExpect(jsonPath( "$.username", is( robertV.getUsername())));
    }

    @Test
    public void givenParams_whenUpdatesAnUserWithIncorrectId_thenReturnsNotFoundException() throws Exception {
        given(mockedUserRepo.findById(robertV.getId())).willReturn(
            java.util.Optional.ofNullable(robertV));
        given(mockedUserRepo.save(any())).willReturn(robertV);

        mockMvc.perform(put("/api/users/" + robertV.getId() + 2)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(robertV)))
            .andExpect(status().isNotFound());
    }

    @Test
    public void givenParams_whenDeletesAnUser_thenReturnsNoContent() throws Exception {
        given(mockedUserRepo.findById(robertV.getId())).willReturn(
            java.util.Optional.ofNullable(robertV));

        mockMvc.perform(delete("/api/users/" + robertV.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(robertV)))
            .andExpect(status().isNoContent());
    }

    @Test
    public void givenParams_whenAttachesBookToAnUser_thenReturnsUserWithAttachedBook() throws Exception {

        given(mockedUserRepo.findById(robertV.getId())).willReturn(
            java.util.Optional.ofNullable(robertV));

        given(mockedBookRepo.findById(theMist.getId())).willReturn(
            java.util.Optional.ofNullable(theMist));

        given(mockedUserRepo.save(any())).willReturn(robertV);

        mockMvc.perform(post("/api/users/attach/" + robertV.getId() + "/" + theMist.getId())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath( "$.books[0].title", is( theMist.getTitle())));
    }

    @Test
    public void givenParams_whenDetachesBookToAnUser_thenReturnsOkStatus() throws Exception {
        robertV.addBook(theMist);

        given(mockedUserRepo.findById(robertV.getId())).willReturn(
            java.util.Optional.ofNullable(robertV));

        given(mockedBookRepo.findById(theMist.getId())).willReturn(
            java.util.Optional.ofNullable(theMist));

        given(mockedUserRepo.save(any())).willReturn(robertV);

        mockMvc.perform(delete("/api/users/detach/" + robertV.getId() + "/" + theMist.getId())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }
}
