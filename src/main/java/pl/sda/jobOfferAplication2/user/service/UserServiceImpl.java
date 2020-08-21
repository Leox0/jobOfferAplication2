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
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {


    public static final String NO_USER_FOUND_FOR_GIVEN_ID = "No user found for given id";
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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
        Optional<UserEntity> optionalUserEntity = userRepository.findById(id);
        if (!optionalUserEntity.isPresent()) {
            throw new UserException(NO_USER_FOUND_FOR_GIVEN_ID);
        }

        return optionalUserEntity.get().toOutput();
    }

    @Override
    public void createUser(UserInput userInput) {
        final String encode = passwordEncoder.encode(userInput.getPassword());
        UserEntity userEntity = new UserEntity(
                userInput.getName(),
                userInput.getLogin(),
                userInput.getCreationDate(),
                encode);
        userRepository.save(userEntity);
    }
}
