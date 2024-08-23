package com.smartcontactmanager.services.impl;

import com.smartcontactmanager.Entity.User;
import com.smartcontactmanager.Error.ResourcesNotFoundException;
import com.smartcontactmanager.Helper.AppConstants;
import com.smartcontactmanager.Helper.CurrentUser;
import com.smartcontactmanager.repositories.UserRepo;
import com.smartcontactmanager.services.EmailService;
import com.smartcontactmanager.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserServices {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private EmailService emailService;

    @Override
    public User saveUser(User user) {

        user.setRolesList(List.of(AppConstants.ROLE_USER));

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        // mail send to person

        String emailToken = UUID.randomUUID().toString();

        user.setEmailToken(emailToken);
        User saveUser = userRepo.save(user);

        String emailLink = CurrentUser.getLinkForEmailVerificatiton(emailToken);

        emailService.sendMail(saveUser.getEmail(), "Verify Account : Smart Contact Manager ", emailLink);

        return saveUser;

    }

    @Override
    public Optional<User> updateUser(User user) {
        User userfound = userRepo.findById(user.getUserId())
                .orElseThrow(() -> new ResourcesNotFoundException("User Not update because user not Found"));

        userfound.setFirstName(user.getFirstName());
        userfound.setLastName(user.getLastName());
        userfound.setEmail(user.getEmail());
        userfound.setPassword(user.getPassword());
        userfound.setPhoneNumber(user.getPhoneNumber());
        userfound.setPicture(user.getPicture());
        userfound.setEmailVerified(user.isEmailVerified());
        userfound.setCloudinaryImagePublicId(user.getCloudinaryImagePublicId());
        userfound.setAbout(user.getAbout());

        // userfound.setProvide(user.getProvide());
        userfound.setActive(user.isEnabled());
        // userfound.setRole(user.getRole());

        userfound.setProviderId(user.getProviderId());

        User save = userRepo.save(userfound);
        return Optional.ofNullable(save);
    }

    @Override
    public User getUserById(String id) {

        User userfound = userRepo.findById(id).orElseThrow(() -> new ResourcesNotFoundException("User Not Found"));

        return userfound;

    }

    @Override
    public List<User> getUsers() {
        return userRepo.findAll();
    }

    @Override
    public void deleteUser(String id) {

        User orElseThrow = userRepo.findById(id).orElseThrow(() -> new ResourcesNotFoundException("User Not Found"));
        userRepo.delete(orElseThrow);

    }

    @Override
    public boolean isUserExists(String id) {
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

        System.out.println("getUserByid____________" + email);
        return userRepo.findByEmail(email).orElse(null);
    }

    @Override
    public User getEmailToken(String token) {

        User user = userRepo.findByEmailToken(token).orElse(null);

        return user;
    }
}
