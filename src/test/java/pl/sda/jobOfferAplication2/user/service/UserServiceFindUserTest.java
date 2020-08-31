package pl.sda.jobOfferAplication2.user.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.sda.jobOfferAplication2.user.entity.UserEntity;
import pl.sda.jobOfferAplication2.user.exception.UserException;
import pl.sda.jobOfferAplication2.user.model.UserInput;
import pl.sda.jobOfferAplication2.user.model.UserOutput;
import pl.sda.jobOfferAplication2.user.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.stream.Collectors;
import static pl.sda.jobOfferAplication2.user.service.UserServiceImpl.*;


@SpringBootTest
public class UserServiceFindUserTest {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    public void shouldGetAllUsers() throws UserException {
        //given
        UserInput userInput1 = new UserInput("Marek", "Marek123", "Marek123@@");
        UserInput userInput2 = new UserInput("Darek", "Darek123", "Darek123@@");
        UserInput userInput3 = new UserInput("Asia", "Asia123", "Asia123@@");
        int expectSizeUserList = 3;
        userService.createUser(userInput1);
        userService.createUser(userInput2);
        userService.createUser(userInput3);
        List<UserOutput> expectUserList = userRepository.findAll()
                .stream()
                .map(UserEntity::toOutput)
                .collect(Collectors.toList());

        //when
        List<UserOutput> allUsers = userService.getAllUsers();

        //then
        assertEquals(expectSizeUserList, allUsers.size());
        for (int i = 0; i < allUsers.size(); i++) {
            assertTrue(allUsers.get(i).getLogin().equals(expectUserList.get(i).getLogin()));
            assertTrue(allUsers.get(i).getUuid().equals(expectUserList.get(i).getUuid()));
        }
    }

    @Test
    public void shouldGetUserById() throws UserException {
        //given
        UserInput userInput1 = new UserInput("Marek", "Marek123", "Marek123@@");
        userService.createUser(userInput1);
        UserOutput exceptUserOutput = userRepository.findAll()
                .stream()
                .findFirst()
                .get()
                .toOutput();

        //when
        UserOutput userById = userService.getUserById(exceptUserOutput.getUuid());

        //then
        assertEquals(exceptUserOutput.getLogin(),userById.getLogin());
        assertEquals(exceptUserOutput.getUuid(),userById.getUuid());
    }

    @Test
    public void shouldThrowExceptionWhenGetUserByIdWhoDoesntExist(){
        //given

        //when
        Executable executable = () -> userService.getUserById(10L);

        //then
        UserException userException = assertThrows(UserException.class, executable);
        assertEquals(NO_USER_FOUND_FOR_GIVEN_ID, userException.getMessage());
    }


}
