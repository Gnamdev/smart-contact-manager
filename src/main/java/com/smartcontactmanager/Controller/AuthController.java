package com.smartcontactmanager.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.smartcontactmanager.Entity.User;
import com.smartcontactmanager.repositories.UserRepo;
import com.smartcontactmanager.services.UserServices;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserServices services;

    @Autowired
    private UserRepo repo;

    // verify email
    @GetMapping("/verify-email")
    public String verifyEmail(@RequestParam("token") String token) {

        User userEmailToken = services.getEmailToken(token);

        if (userEmailToken != null) {

            if (userEmailToken.getEmailToken().equals(token)) {

                userEmailToken.setEmailVerified(true);
                userEmailToken.setActive(true);
                repo.save(userEmailToken);

            }

            return "success";
        }

        return "error";
    }

}
