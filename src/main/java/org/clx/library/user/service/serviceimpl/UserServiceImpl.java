package org.clx.library.user.service.serviceimpl;

import lombok.RequiredArgsConstructor;
import org.clx.library.user.config.JwtProvider;
import org.clx.library.user.model.User;
import org.clx.library.user.repository.UserRepository;
import org.clx.library.user.service.UserService;
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

    public User registerUser(User user, Integer id) {
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
    public List<User> getAllUsers() {
        // Retrieve all users from the database
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(Integer id) {
        // Retrieve a user by ID from the database
        return userRepository.findById(id);
    }

    @Override
    public User createUser(User user, Integer id) {

        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Integer id) {
        // Check if the user exists before deleting
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new RuntimeException("User not found with id: " + id);
        }
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        // Retrieve a user by email from the database
        return null;
//        return userRepository.findByEmail(email);
    }
}
