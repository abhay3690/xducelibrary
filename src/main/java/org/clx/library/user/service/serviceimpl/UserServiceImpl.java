package org.clx.library.user.service.serviceimpl;

import lombok.RequiredArgsConstructor;
import org.clx.library.user.config.JwtProvider;
import org.clx.library.user.dto.AccountInfo;
import org.clx.library.user.dto.EmailDetails;
import org.clx.library.user.dto.LibraryResponse;
import org.clx.library.user.dto.UserRequest;
import org.clx.library.user.exception.UserException;
import org.clx.library.user.model.User;
import org.clx.library.user.repository.UserRepository;
import org.clx.library.user.service.EmailService;
import org.clx.library.user.service.UserService;
import org.clx.library.utils.AccountUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class); // Create Logger instance

    private final UserRepository userRepository;
    private final EmailService emailService;

    public User registerUser(User user, Integer id) {


//un commit the code

        logger.info("Registering user: {}", user.getEmail());

        User newUser = new User();

        newUser.setId(user.getId());
        newUser.setName(user.getName());
        newUser.setCollegeName(user.getCollegeName());
        newUser.setContactNumber(user.getContactNumber());
        newUser.setPassword(user.getPassword());
        newUser.setEmail(user.getEmail());
        newUser.setGender(user.getGender());

        logger.info("Saving user with email: {}", newUser.getEmail());
        User savedUser = userRepository.save(newUser);
        logger.info("User registered successfully: {}", savedUser.getEmail());

        return savedUser;



//    public User registerUser(User user, Integer id) {
//        logger.info("Registering user: {}", user.getEmail());
//
//        User newUser = new User();
//
//        newUser.setId(user.getId());
//        newUser.setName(user.getName());
//        newUser.setCollegeName(user.getCollegeName());
//        newUser.setContactNumber(user.getContactNumber());
//        newUser.setPassword(user.getPassword());
//        newUser.setEmail(user.getEmail());
//        newUser.setGender(user.getGender());
//
//        logger.info("Saving user with email: {}", newUser.getEmail());
//        User savedUser = userRepository.save(newUser);
//        logger.info("User registered successfully: {}", savedUser.getEmail());
//
//        return savedUser;
//    }
    }
    @Override
    public User findUserByJwt(String jwt) {
        logger.info("Finding user by JWT: {}", jwt);
        String email = JwtProvider.getEmailFromJwtToken(jwt);
        User user = userRepository.findByEmail(email);
        if (user != null) {
            logger.info("User found: {}", user.getEmail());
        } else {
            logger.warn("No user found for email: {}", email);
        }
        return user;
    }

    @Override
    public User findUserById(Integer userId) throws UserException {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            return user.get();
        }
        throw new UserException("User not exist with userId" + userId);
    }



    @Override
    public User updateUser(User user, Integer userId) throws UserException {
        Optional<User> user1 = userRepository.findById(userId);
        if (user1.isEmpty()) {
            throw new UserException("User does not exist with id " + userId);
        }
        User oldUser = user1.get();
        if (user.getName() != null) {
            oldUser.setName(user.getName());
        }
        if (user.getContactNumber() != null) {
            oldUser.setContactNumber(user.getContactNumber());
        }
        if (user.getEmail() != null) {
            oldUser.setEmail(user.getEmail());
        }
        if (user.getGender() != null) {
            oldUser.setGender(user.getGender());
        }
        if (user.getCollegeName() != null) {
            oldUser.setCollegeName(user.getCollegeName());
        }
        return userRepository.save(oldUser);
    }
    @Override
    public List<User> searchUser(String query) {

        return userRepository.searchUser(query);
    }

    @Override
    public LibraryResponse registerUser(UserRequest userRequest) {
        if (userRepository.existsByEmail(userRequest.getEmail())) {
            return LibraryResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_EXISTS_CDOE) // Fixed typo here
                    .responseMessage(AccountUtils.ACCOUNT_EXISTS_MESSAGE)
                    .accountInfo(null)
                    .build();
        }
        User newUser = User.builder()
                .name(userRequest.getName())
                .gender(userRequest.getGender())
                .collegeName(userRequest.getCollegeName())
                .contactNumber(userRequest.getContactNumber())
                .email(userRequest.getEmail())
                .status("ACTIVE")
                .build();
        User savedUser = userRepository.save(newUser);
        EmailDetails emailDetails = EmailDetails.builder()
                .recupient(savedUser.getEmail())
                .subject("ACCOUNT CREATION")
                .messageBody("Congratulation! Your account has been successfully created \nYour Account Details : \n" + " Account Name : " + savedUser.getName() + " " + "\n Account Number : " + savedUser.getContactNumber())
                .build();
        emailService.sendEmailAlert(emailDetails);

        return LibraryResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_CREATION_SUCCESS)
                .responseMessage(AccountUtils.ACCOUNT_CREATION_MESSAGE)
                .accountInfo(AccountInfo.builder()
                        .userName(savedUser.getName() + " " + savedUser.getContactNumber())// Corrected here
                        .build())
                .build();

    }
}
