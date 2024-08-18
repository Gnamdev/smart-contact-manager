package com.smartcontactmanager.services;

import java.util.List;
import java.util.Optional;
import com.smartcontactmanager.Entity.User;

public interface UserServices {

    User saveUser(User user);

    Optional<User> updateUser(User user);

    User getUserById(String id);

    List<User> getUsers();

    void deleteUser(String id);

    boolean isUserExists(String id);

    boolean userExitsByEmail(String email);

    User getUserByEmail(String email);

    User getEmailToken(String token);

}
