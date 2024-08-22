package com.smartcontactmanager.Controller;

import com.smartcontactmanager.Entity.User;
import com.smartcontactmanager.Form.*;
import com.smartcontactmanager.Helper.AppConstants;
import com.smartcontactmanager.Helper.Message;
import com.smartcontactmanager.Helper.MessageType;
import com.smartcontactmanager.repositories.UserRepo;
import com.smartcontactmanager.services.ImageService;
import com.smartcontactmanager.services.UserServices;

import jakarta.validation.Valid;

import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MyController {

    private final UserServices userServices;
    private final ImageService imageService;

    public MyController(UserRepo userRepo, UserServices userServices, ImageService imageService) {

        this.userServices = userServices;
        this.imageService = imageService;
    }

    @RequestMapping("")
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

    @GetMapping("/login")
    public String loginPage() {

        System.out.println("login page");
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
            jakarta.servlet.http.HttpSession session, Model model, @RequestParam("userImage") MultipartFile multipart) {

        System.out.println("User-controller " + "------>" + userFormData);

        // validate
        if (errors.hasErrors()) {

            Message mgs = Message.builder()
                    .content("Please provide right information !")
                    .type(MessageType.red)
                    .build();
            session.setAttribute("message", mgs);

            return "register";
        }
        // save

        try {
            // Create new User object and set its properties
            User user = new User();
            user.setEmail(userFormData.getEmail());
            user.setPassword(userFormData.getPassword());
            user.setFirstName(userFormData.getFirstName());
            user.setLastName(userFormData.getLastName());
            user.setPhoneNumber(userFormData.getPhoneNumber());
            user.setAbout(userFormData.getAbout());

            // Handle user image upload
            String fileURL;
            String publicId;
            String filename = UUID.randomUUID().toString();

            if (multipart != null && !multipart.isEmpty()) {
                fileURL = imageService.uploadImage(multipart, filename);
                publicId = filename;
            } else {
                fileURL = AppConstants.DEFAULT_IMAGE;
                publicId = filename;
            }

            user.setPicture(fileURL);
            user.setCloudinaryImagePublicId(publicId);

            // Save user
            userServices.saveUser(user);

            // Display success message
            Message registeredSuccessfully = Message.builder()
                    .content("Registered Successfully")
                    .type(MessageType.green)
                    .build();

            session.setAttribute("message", registeredSuccessfully);

            // Redirect to login page after successful registration
            return "redirect:/login";
        } catch (Exception e) {
            e.printStackTrace();
            Message mgs = Message.builder()
                    .content("An error occurred during registration. Please try again.")
                    .type(MessageType.red)
                    .build();
            session.setAttribute("message", mgs);
            return "register";
        }

    }

}
