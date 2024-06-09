package com.scm.scm20.services;

import java.util.List;
import java.util.Optional;

import com.scm.scm20.entities.User;

public interface UserService {

    User saveUser(User user);
    Optional<User> getUserByID(String id);
    Optional<User> updateUser(User user);
    void deleteUser(String id);
    boolean isUserExit(String userId);
    boolean isUserExitByEmail(String email);
    List<User> getAllUsers();
    User getUserByEmail(String email);

}
