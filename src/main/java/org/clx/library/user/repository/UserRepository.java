package org.clx.library.user.repository;

import org.clx.library.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmail(String email);

    Boolean existsByEmail(String email);  // Fixed typo here

    @Query("select u from User u where u.name LIKE %:query% OR u.email LIKE %:query%")
    List<User> searchUser(@Param("query") String query);
}