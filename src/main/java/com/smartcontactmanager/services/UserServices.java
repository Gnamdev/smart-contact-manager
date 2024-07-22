package com.smartcontactmanager.services;


import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import com.smartcontactmanager.Entity.User ;


public interface UserServices {

    User saveUser(User user);
    Optional<User> updateUser(User user );

    Optional<User> getUser(Integer id);
    List<User> getUsers();

    void deleteUser(Integer id);
    boolean isUserExists(Integer id);
    boolean userExitsByEmail(String email);

    User getUserByEmail(String email);


}
