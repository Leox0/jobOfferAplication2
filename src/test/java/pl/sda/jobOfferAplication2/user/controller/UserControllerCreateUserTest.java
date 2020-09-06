package pl.sda.jobOfferAplication2.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.sda.jobOfferAplication2.user.entity.UserEntity;
import pl.sda.jobOfferAplication2.user.exception.UserException;
import pl.sda.jobOfferAplication2.user.model.UserInput;
import pl.sda.jobOfferAplication2.user.repository.UserRepository;
import pl.sda.jobOfferAplication2.user.service.UserService;

import java.lang.reflect.Executable;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pl.sda.jobOfferAplication2.user.controller.UserController.USERS_MAPPING;
import static pl.sda.jobOfferAplication2.user.service.UserServiceImpl.*;

@AutoConfigureMockMvc
@SpringBootTest
public class UserControllerCreateUserTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    public void shouldCreateUserCorrectly() throws Exception {
        //given
        final String USER_PATH = USERS_MAPPING;
        String expectUserName = "Marek";
        String expectUserLogin = "Marek123";
        String expectUserPassword = "Marek123@@";
        UserInput userInput = new UserInput(expectUserName, expectUserLogin, expectUserPassword);
        int expectSizeUserRepository = 1;
        final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(USER_PATH)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(toJson(userInput));

        //when
        final ResultActions resultActions = mockMvc.perform(requestBuilder);

        //then
        List<UserEntity> allUsers = userRepository.findAll();
        assertTrue(allUsers.size() == expectSizeUserRepository);
        assertTrue(userRepository.existsUserEntityByLogin(expectUserLogin));
        resultActions.andExpect(status().isCreated());
    }

    @Test
    public void shouldGiveStatusNotFoundWhenCreateUserWithTheSameLogin() throws Exception {
        //given
        final String USER_PATH = USERS_MAPPING;

        UserInput userInput1 = new UserInput("Marek", "Marek123", "Marek123@@");
        UserInput userInput2 = new UserInput("Darek", "Marek123", "Darek123@@");
        userService.createUser(userInput1);
        final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(USER_PATH)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(toJson(userInput2));

        //when
        ResultActions resultActions = mockMvc.perform(requestBuilder);

        //then
        resultActions.andExpect(status().isNotFound());
    }

//jak sprawdzić komunikat wyrzucany w body

    @Test
    public void shouldGiveStatusNotFoundWhenCreateUserWithIncorrectLogin() {
        //to implement
    }

    @Test
    public void shouldGiveStatusNotFoundWhenCreateUserWithIncorrectPassword() {
        //to implement

    }


    private static String toJson(final Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
