package wolox.training.controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import wolox.training.models.User;
import wolox.training.repositories.BookRepository;
import wolox.training.repositories.UserRepository;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    private User javier;
    private User marihe;
    private User robertV;

    @BeforeEach
    void setUp(){
        javier = new User("JaviMo", "Javier Moreno", LocalDate.parse("1980-10-08"));
        marihe = new User("MariHe", "Maria Haize", LocalDate.parse("1990-11-04"));
        robertV = new User("Robert Velvet", "Robert Velvet", LocalDate.parse("1975-04-11"));
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
}
