package pl.sda.jobOfferAplication2.user.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable; //jupiter do testów Executable = () ->
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.sda.jobOfferAplication2.user.entity.UserEntity;
import pl.sda.jobOfferAplication2.user.exception.UserException;
import pl.sda.jobOfferAplication2.user.model.UserInput;
import pl.sda.jobOfferAplication2.user.repository.UserRepository;


import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static pl.sda.jobOfferAplication2.user.service.UserServiceImpl.*;

@SpringBootTest
public class UserServiceCreateUserTest {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    public void shouldCreateUserCorrectly() throws UserException {
        //given
        String expectUserName = "Marek";
        String expectUserLogin = "Marek123";
        String expectUserPassword = "Marek123@@";
        UserInput userInput = new UserInput(expectUserName, expectUserLogin, expectUserPassword);
        int expectSizeUserRepository = 1;

        //when
        userService.createUser(userInput);

        //then
        List<UserEntity> allUsers = userRepository.findAll();
        assertTrue(allUsers.size() == expectSizeUserRepository);
        String userLogin = allUsers
                .stream()
                .findFirst()
                .get()
                .toOutput()
                .getLogin();
        assertEquals(expectUserLogin, userLogin);
    }

    @Test
    public void shouldCreateMoreThenOneUser() throws UserException {
        //given
        UserInput userInput1 = new UserInput("Marek", "Marek123", "Marek123@@");
        UserInput userInput2 = new UserInput("Darek", "Darek123", "Darek123@@");
        UserInput userInput3 = new UserInput("Asia", "Asia123", "Asia123@@");
        int expectSizeUserRepository = 3;

        //when
        userService.createUser(userInput1);
        userService.createUser(userInput2);
        userService.createUser(userInput3);

        //then
        List<UserEntity> allUsers = userRepository.findAll();
        assertTrue(allUsers.size() == expectSizeUserRepository);
    }

    @Test
    public void shouldThrowExceptionWhenCreateUserWithTheSameLogin() throws UserException {
        //given
        UserInput userInput1 = new UserInput("Marek", "Marek123", "Marek123@@");
        UserInput userInput2 = new UserInput("Darek", "Marek123", "Darek123@@");
        userService.createUser(userInput1);
        int expectSizeUserRepository = 1;

        //when
        Executable executable = () -> userService.createUser(userInput2);

        //then
        List<UserEntity> allUsers = userRepository.findAll();
        assertTrue(allUsers.size() == expectSizeUserRepository);

        UserException userException = assertThrows(UserException.class, executable);
        assertEquals(USER_FOR_GIVEN_LOGIN_IS_EXIST, userException.getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenCreateUserWithIncorrectLogin() {
        //given
        UserInput userInput = new UserInput("Marek", "Mar1", "Marek123@@");

        //when
        Executable executable = () -> userService.createUser(userInput);

        //then
        assertTrue(userRepository.findAll().isEmpty());
        UserException userException = assertThrows(UserException.class,executable);
        assertEquals(USER_LOGIN_IS_TOO_SHORT, userException.getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenCreateUserWithIncorrectPassword() {
        //given
        UserInput userInput = new UserInput("Marek", "Marek123", "marek");

        //when
        Executable executable = () -> userService.createUser(userInput);

        //then
        assertTrue(userRepository.findAll().isEmpty());
        UserException userException = assertThrows(UserException.class,executable);
        assertEquals(USER_PASSWORD_IS_INCORRECT, userException.getMessage());
    }

}
