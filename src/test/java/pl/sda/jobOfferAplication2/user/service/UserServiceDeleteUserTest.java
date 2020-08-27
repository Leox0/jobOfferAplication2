package pl.sda.jobOfferAplication2.user.service;

import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.sda.jobOfferAplication2.user.repository.UserRepository;

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

}
