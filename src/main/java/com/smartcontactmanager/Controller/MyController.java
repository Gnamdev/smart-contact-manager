package com.smartcontactmanager.Controller;

import com.smartcontactmanager.Entity.User;
import com.smartcontactmanager.Form.*;
import com.smartcontactmanager.Helper.Message;
import com.smartcontactmanager.Helper.MessageType;
import com.smartcontactmanager.repositories.UserRepo;
import com.smartcontactmanager.services.UserServices;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.*;

@Controller
public class MyController {

    private final UserServices userServices;

    public MyController(UserRepo userRepo, UserServices userServices) {

        this.userServices = userServices;
    }

    @RequestMapping("/home")
    public String home(Model model) {
        System.out.println("inside home controller");

        model.addAttribute("title", "Home Page");
        model.addAttribute("Name", "welcome goutam in Home page..");
        return "home";
    }

    @RequestMapping("/about")
    public String about() {
        System.out.println("about page");
        return "about";
    }

    @RequestMapping("/service")
    public String servicePage() {

        System.out.println("service page");
        return "service";
    }

    @RequestMapping("/contact")
    public String contactPage() {

        System.out.println("contact page");
        return "contact";
    }

    @RequestMapping("/login")
    public String loginPage() {

        // System.out.println("login page");
        return "login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {

        System.out.println("register page");

        UserFormData userData = new UserFormData();

        model.addAttribute("user", userData);
        return "register";
    }

    // for handling form data
    @PostMapping("/do-register")
    public String processFormData(@Valid @ModelAttribute("user") UserFormData userFormData, BindingResult errors,
            jakarta.servlet.http.HttpSession session, Model model) {

        System.out.println(userFormData);

        // fetch

        // validate
        if (errors.hasErrors()) {
            // model.addAttribute("user", userFormData);

            return "register";
        }
        // save

        User user = new User();
        user.setEmail(userFormData.getEmail());
        user.setPassword(userFormData.getPassword());
        user.setFirstName(userFormData.getFirstName());
        user.setLastName(userFormData.getLastName());
        user.setPassword(userFormData.getPassword());
        user.setPhoneNumber(userFormData.getPhoneNumber());

        user.setProfilePic(
                "https://in.pinterest.com/pin/default-avatar-profile-icon-of-social-media-user--947022627871095943/");

        // save
        User userSave = userServices.saveUser(user);

        // msg success
        Message registeredSuccessfully = Message.builder()
                .content("Registered Successfully")
                .type(MessageType.green)
                .build();

        session.setAttribute("message", registeredSuccessfully);

        // redirect

        return "redirect:/register";

    }

}
