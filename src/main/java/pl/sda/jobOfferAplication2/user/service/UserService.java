package pl.sda.jobOfferAplication2.user.service;

import pl.sda.jobOfferAplication2.user.exception.UserException;
import pl.sda.jobOfferAplication2.user.model.UserInput;
import pl.sda.jobOfferAplication2.user.model.UserOutput;

import java.util.List;

public interface UserService {
    List<UserOutput> getAllUsers();
    UserOutput getUserById(Long id) throws UserException;
    void createUser(UserInput userInput) throws UserException;
    void deleteUserById(Long id) throws UserException;
}
