package pl.sda.jobOfferAplication2.user.service;

import pl.sda.jobOfferAplication2.user.model.UserInput;
import pl.sda.jobOfferAplication2.user.model.UserOutput;

import java.util.List;

public interface UserService {
    List<UserOutput> getAllUsers();
    UserService getUserById(Long id);
    void createUser(UserInput userInput);


}
