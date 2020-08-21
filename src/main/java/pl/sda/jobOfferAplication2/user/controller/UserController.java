package pl.sda.jobOfferAplication2.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.sda.jobOfferAplication2.user.exception.UserException;
import pl.sda.jobOfferAplication2.user.model.UserInput;
import pl.sda.jobOfferAplication2.user.model.UserOutput;
import pl.sda.jobOfferAplication2.user.service.UserService;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserOutput>> getAllUsers() {
        List<UserOutput> allUsers = userService.getAllUsers();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(allUsers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserOutput> getUserById(@PathVariable(value = "id") Long id) throws UserException {
        UserOutput userById = userService.getUserById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userById);
    }

    @PostMapping
    public ResponseEntity<Void> postUser(@RequestBody UserInput userInput) throws UserException {
        userService.createUser(userInput);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();

    }

}
