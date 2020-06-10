package wolox.training.controllers;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import wolox.training.models.User;
import wolox.training.repositories.UserRepository;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(UserController.class)

public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
   /*

    @MockBean
    private UserRepository userRepo;

    @MockBean
    private User alex;

    @BeforeEach
    void setUp(){
        alex = new User();
    }

    @Test
    public void givenUsers_whenGetUsers_thenReturnJsonArray() throws Exception {
        List<User> allUser = Arrays.asList(alex);
        given(userRepo.findAll()).willReturn(allUser);

        //mockMvc.perform(get("/all")).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
        mockMvc.perform(get("/all"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].name", is(alex.getName() )));
    }*/

    @Test
    public void givenUsers_whenGetUsers_thenReturnJsonArray() throws Exception {
        mockMvc.perform(get("/api/users/all"))
            .andExpect(status().isOk());
    }
}
