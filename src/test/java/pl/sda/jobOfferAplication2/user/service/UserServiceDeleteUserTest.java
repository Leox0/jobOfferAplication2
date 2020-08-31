package pl.sda.jobOfferAplication2.user.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.sda.jobOfferAplication2.user.exception.UserException;
import pl.sda.jobOfferAplication2.user.model.UserInput;
import pl.sda.jobOfferAplication2.user.model.UserOutput;
import pl.sda.jobOfferAplication2.user.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.*;
import static pl.sda.jobOfferAplication2.user.service.UserServiceImpl.NO_USER_FOUND_FOR_GIVEN_ID;

@SpringBootTest
public class UserServiceDeleteUserTest {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    public void shouldDeleteUserCorrectly() throws UserException {
        //given
        UserInput userInput1 = new UserInput("Marek", "Marek123", "Marek123@@");
        userService.createUser(userInput1);
        UserOutput exceptUserOutput = userRepository.findAll()
                .stream()
                .findFirst()
                .get()
                .toOutput();

        //when
        userService.deleteUserById(exceptUserOutput.getUuid());
        //then
        assertTrue(userRepository.findAll().isEmpty());

    }

    @Test
    public void shouldThrowExceptionWhenDeleteUserWhoDoesntExist(){
        //given

        //when
        Executable executable = () -> userService.deleteUserById(10L);

        //then
        UserException userException = assertThrows(UserException.class, executable);
        assertEquals(NO_USER_FOUND_FOR_GIVEN_ID, userException.getMessage());
    }

}
