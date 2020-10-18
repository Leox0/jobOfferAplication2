package pl.sda.jobOfferAplication2.user.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.sda.jobOfferAplication2.user.entity.UserEntity;
import pl.sda.jobOfferAplication2.user.exception.UserException;
import pl.sda.jobOfferAplication2.user.model.UserInput;
import pl.sda.jobOfferAplication2.user.model.UserOutput;
import pl.sda.jobOfferAplication2.user.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    public static final String NO_USER_FOUND_FOR_GIVEN_ID = "No user found for given id";
    public static final String USER_FOR_GIVEN_LOGIN_IS_EXIST = "User for given login is exist";
    public static final String USER_LOGIN_IS_TOO_SHORT = "User login is too short";
    public static final String USER_PASSWORD_IS_INCORRECT = "    Password is incorrect: \n" +
            "    Must have at least one numeric character\n" +
            "    Must have at least one lowercase character\n" +
            "    Must have at least one uppercase character\n" +
            "    Must have at least one special symbol among @#$%\n" +
            "    Password length should be between 8 and 20";
    public static final int CORRECT_LENGHT_USER_LOGIN = 6;
    public static final String CORRECT_PASSWORD_PATTERN = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,20}$";
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void createUser(UserInput userInput) throws UserException {
        validateUserInput(userInput);
        final String encode = passwordEncoder.encode(userInput.getPassword());
        UserEntity userEntity = new UserEntity(
                userInput.getName(),
                userInput.getLogin(),
                userInput.getCreationDate(),
                encode);
        userRepository.save(userEntity);
    }

    @Override
    public List<UserOutput> getAllUsers() {
        return userRepository
                .findAll()
                .stream()
                .map(UserEntity::toOutput)
                .collect(Collectors.toList());
    }

    @Override
    public UserOutput getUserById(Long id) throws UserException {
        Optional<UserEntity> optionalUserEntity = getOptionalUserEntityById(id);
        return optionalUserEntity.get().toOutput();
    }

    private Optional<UserEntity> getOptionalUserEntityById(Long id) throws UserException {
        Optional<UserEntity> optionalUserEntity = userRepository.findById(id);
        if (!optionalUserEntity.isPresent()) {
            throw new UserException(NO_USER_FOUND_FOR_GIVEN_ID);
        }
        return optionalUserEntity;
    }

    private void validateUserInput(UserInput userInput) throws UserException {
        if (userRepository.existsUserEntityByLogin(userInput.getLogin())) {
            throw new UserException(USER_FOR_GIVEN_LOGIN_IS_EXIST);
        }
        if (!validateUserLogin(userInput.getLogin())) {
            throw new UserException(USER_LOGIN_IS_TOO_SHORT);
        }
        if (!validateUserPassword(userInput.getPassword())) {
            throw new UserException(USER_PASSWORD_IS_INCORRECT);
        }
    }

    private boolean validateUserPassword(String password) {
        String regex = CORRECT_PASSWORD_PATTERN;
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    private boolean validateUserLogin(String login) {
        return login.length() > CORRECT_LENGHT_USER_LOGIN;
    }

    @Override
    public void deleteUserById(Long id) throws UserException {
        getOptionalUserEntityById(id);
        userRepository.deleteById(id);
    }

}
