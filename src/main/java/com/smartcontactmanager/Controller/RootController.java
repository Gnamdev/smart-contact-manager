package com.smartcontactmanager.Controller;

import com.smartcontactmanager.Controller.UserController.UserController;
import com.smartcontactmanager.Entity.User;
import com.smartcontactmanager.Helper.CurrentUser;
import com.smartcontactmanager.services.UserServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class RootController {
    @Autowired
    private UserServices userServices ;

    Logger logger = LoggerFactory.getLogger(RootController.class);
    @ModelAttribute
    public void getLoggedInUserInformation(Authentication authentication, Model model){


        if(authentication == null){ return;}
        logger.info("adding logged in user information");

        String  userName  = CurrentUser.getEmailWithLoggedUser(authentication);


        logger.info("user logged  in " + userName);

        // DB se data fech


            User userByEmail = userServices.getUserByEmail(userName);

            System.out.println("user info" + userByEmail.getEmail() + " " + userByEmail.getFirstName());

            model.addAttribute("loggedInUser", userByEmail);



    }
}
