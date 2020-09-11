package pl.sda.jobOfferAplication2.user.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.sda.jobOfferAplication2.user.model.UserInput;
import pl.sda.jobOfferAplication2.user.model.UserOutput;
import pl.sda.jobOfferAplication2.user.repository.UserRepository;
import pl.sda.jobOfferAplication2.user.service.UserService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pl.sda.jobOfferAplication2.user.controller.UserController.USERS_MAPPING;
import static pl.sda.jobOfferAplication2.user.service.UserServiceImpl.NO_USER_FOUND_FOR_GIVEN_ID;

@AutoConfigureMockMvc
@SpringBootTest
public class UserControllerDeleteUserTest {
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
    public void shouldDeleteUserCorrectly() throws Exception {
        //given
        UserInput userInput1 = new UserInput("Marek", "Marek123", "Marek123@@");
        userService.createUser(userInput1);
        UserOutput userOutput = userRepository.findAll()
                .stream()
                .findAny()
                .get()
                .toOutput();
        final String USER_PATH = USERS_MAPPING + "/" + userOutput.getUuid();
        final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete(USER_PATH)
                .contentType(MediaType.APPLICATION_JSON_VALUE);

        //when
        ResultActions resultActions = mockMvc.perform(requestBuilder);
        //then
        resultActions.andExpect(status().isOk());
    }

    @Test
    public void shouldGiveStatusNotFoundWhenGetUserByIdWhoDoesntExist() throws Exception {
        //given
        Long userId = 5L;
        final String USER_PATH = USERS_MAPPING + "/" + userId;
        final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete(USER_PATH)
                .contentType(MediaType.APPLICATION_JSON_VALUE);
        //when
        ResultActions resultActions = mockMvc.perform(requestBuilder);

        //then
        resultActions.andExpect(status().isNotFound());
        String contentAsString = resultActions.andReturn().getResponse().getContentAsString();
        assertEquals(NO_USER_FOUND_FOR_GIVEN_ID, contentAsString);
    }

}
