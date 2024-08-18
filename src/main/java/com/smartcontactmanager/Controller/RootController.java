// package com.smartcontactmanager.Controller;

// import com.smartcontactmanager.Entity.User;
// import com.smartcontactmanager.Helper.CurrentUser;
// import com.smartcontactmanager.services.UserServices;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.core.Authentication;
// import org.springframework.ui.Model;
// import org.springframework.web.bind.annotation.ControllerAdvice;
// import org.springframework.web.bind.annotation.ModelAttribute;

// @ControllerAdvice
// public class RootController {
//     @Autowired
//     private UserServices userServices;

//     Logger logger = LoggerFactory.getLogger(RootController.class);

//     @ModelAttribute
//     public void getLoggedInUserInformation(Authentication authentication, Model model) {

//         if (authentication == null) {
//             return;
//         }
//         logger.info("adding logged in user information");

//         String userName = CurrentUser.getEmailWithLoggedUser(authentication);

//         logger.info("user logged  in form root-controoler " + userName);

//         // DB se data fech

//         if (userName != null) {
//             User userByEmail = userServices.getUserByEmail(userName);
//             System.out.println("user info" + userByEmail.getEmail() + " " + userByEmail.getFirstName());

//             model.addAttribute("loggedInUser", userByEmail);
//         } else {
//             System.out.println("exception in root controller +++++++++++");
//         }

//     }
// }

package com.smartcontactmanager.Controller;

import com.smartcontactmanager.Entity.Contact;
import com.smartcontactmanager.Entity.User;
import com.smartcontactmanager.Helper.CurrentUser;
import com.smartcontactmanager.repositories.ContactRepo;
import com.smartcontactmanager.services.ContactService;
import com.smartcontactmanager.services.UserServices;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class RootController {

    private final UserServices userServices;
    private final ContactService contactService;

    private static final Logger logger = LoggerFactory.getLogger(RootController.class);

    private final ContactRepo contactRepo;

    public RootController(UserServices userServices, ContactService contactService, ContactRepo contactRepo) {
        this.userServices = userServices;
        this.contactService = contactService;
        this.contactRepo = contactRepo;
    }

    @ModelAttribute
    public void getLoggedInUserInformation(Authentication authentication, Model model) {
        if (authentication == null) {
            return;
        }

        String userName = CurrentUser.getEmailWithLoggedUser(authentication);
        if (userName != null) {
            User userByEmail = userServices.getUserByEmail(userName);
            if (userByEmail != null) {

                List<Contact> contacts = userByEmail.getContacts();

                // List<Contact> list =
                List<Contact> favoriteContactsByUserId = contactRepo
                        .findFavoriteContactsByUserId(userByEmail.getUserId());

                // System.out.println("size+++++++++++" + favoriteContactsByUserId.size());

                if (!favoriteContactsByUserId.isEmpty()) {

                    model.addAttribute("favContact", favoriteContactsByUserId.size());
                }

                model.addAttribute("contactCount", contacts.size());
                model.addAttribute("loggedInUser", userByEmail);
                logger.info("Added logged in user information for user: {}", userByEmail.getEmail());

            } else {
                logger.warn("User not found with email: {}", userName);
            }
        } else {
            logger.warn("Username is null in authentication object");
        }
    }
}
