package com.smartcontactmanager.services.impl;

import com.smartcontactmanager.Entity.User;
import com.smartcontactmanager.Error.ResourcesNotFoundException;
import com.smartcontactmanager.Helper.AppConstants;
import com.smartcontactmanager.repositories.UserRepo;
import com.smartcontactmanager.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserServices {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User saveUser(User user) {

        user.setRolesList(List.of(AppConstants.ROLE_USER));

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    @Override
    public Optional<User> updateUser(User user) {
        User userfound = userRepo.findById(user.getUserId())
                .orElseThrow(() -> new ResourcesNotFoundException("User Not Found"));

        userfound.setFirstName(user.getFirstName());
        userfound.setLastName(user.getLastName());
        userfound.setEmail(user.getEmail());
        userfound.setPassword(user.getPassword());
        userfound.setPhoneNumber(user.getPhoneNumber());
        userfound.setProfilePic(user.getProfilePic());
        userfound.setEmailVerified(user.isEmailVerified());

        // userfound.setProvide(user.getProvide());
        userfound.setActive(user.isEnabled());
        userfound.setRole(user.getRole());

        userfound.setProviderId(user.getProviderId());

        User save = userRepo.save(userfound);
        return Optional.ofNullable(save);
    }

    @Override
    public Optional<User> getUser(Integer id) {

        User userfound = userRepo.findById(id).orElseThrow(() -> new ResourcesNotFoundException("User Not Found"));

        return Optional.ofNullable(userfound);
    }

    @Override
    public List<User> getUsers() {
        return userRepo.findAll();
    }

    @Override
    public void deleteUser(Integer id) {

        userRepo.findById(id).orElseThrow(() -> new ResourcesNotFoundException("User Not Found"));

    }

    @Override
    public boolean isUserExists(Integer id) {
        User user = userRepo.findById(id).orElse(null);
        return user != null ? true : false;
    }

    @Override
    public boolean userExitsByEmail(String email) {
        User user = userRepo.findByEmail(email).orElseThrow(() -> new ResourcesNotFoundException("User Not Found"));
        return user != null ? true : false;
    }

    @Override
    public User getUserByEmail(String email) {

        return userRepo.findByEmail(email).orElse(null);
    }
}
