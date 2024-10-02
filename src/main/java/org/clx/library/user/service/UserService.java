package org.clx.library.user.service;

import org.clx.library.user.model.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {
    public User registerUser(User user, Integer id);
    public User findUserByJwt(String jwt);

    List<User> getAllUsers();
    Optional<User> getUserById(Integer id);
    User createUser(User user, Integer id);
    void deleteUser(Integer id);
    Optional<User> getUserByEmail(String email);


}
