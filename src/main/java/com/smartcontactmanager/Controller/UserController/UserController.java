package com.smartcontactmanager.Controller.UserController;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.smartcontactmanager.Entity.User;
import com.smartcontactmanager.Form.UserProfileUpdate;
import com.smartcontactmanager.Helper.MessageType;
import com.smartcontactmanager.services.ImageService;
import com.smartcontactmanager.services.UserServices;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserServices userServices;

    @Autowired
    private ImageService imageService;

    @RequestMapping("/dashboard")
    public String userDeshbord() {
        return "user/dashboard";
    }

    @GetMapping("/profile")
    public String userProfile(Authentication authentication, Model model) {

        return "user/profile";
    }

    @RequestMapping("/profile/update/{id}")
    public String profileUpdate(@PathVariable String id, Model model) {
        System.out.println("User update ID: " + id);

        User user = userServices.getUserById(id);
        if (user == null) {
            return "error/404"; // Or any other error page
        }

        UserProfileUpdate profileUpdate = UserProfileUpdate.builder()
                .email(user.getEmail())
                .name(user.getFirstName())
                .lastName(user.getLastName())
                .about(user.getAbout())
                .phoneNumber(user.getPhoneNumber())
                .build();

        model.addAttribute("userForm", profileUpdate);
        model.addAttribute("userId", id);
        return "user/user_update";

    }

    @PostMapping("/profile/update/{id}")
    public String userProfileUpdate(@PathVariable String id,
            @Valid @ModelAttribute("userForm") UserProfileUpdate userProfile,
            BindingResult bindingResult,
            @RequestParam("picture") MultipartFile multipartFile,
            HttpSession httpSession) {

        if (bindingResult.hasErrors()) {
            System.out.println("id + " + id);
            System.out.println("Data in user controller: " + userProfile);
            System.out.println("Error count: " + bindingResult.getFieldError());
            httpSession.setAttribute("message",
                    com.smartcontactmanager.Helper.Message.builder()
                            .content("Invalid information. Please provide correct information!")
                            .type(MessageType.red).build());
            return "user/user_update";
        }

        User userById = userServices.getUserById(id);
        if (userById == null) {
            return "error/404"; // Or any other error page
        }

        userById.setUserId(id);
        userById.setActive(true);
        userById.setAbout(userProfile.getAbout());
        userById.setEmail(userProfile.getEmail());
        userById.setFirstName(userProfile.getName());
        userById.setLastName(userProfile.getLastName());
        userById.setPhoneNumber(userProfile.getPhoneNumber());

        if (multipartFile != null && !multipartFile.isEmpty()) {
            String fileName = UUID.randomUUID().toString();
            String imageUrl = imageService.uploadImage(multipartFile, fileName);
            userById.setCloudinaryImagePublicId(fileName);
            userById.setPicture(imageUrl);
        }

        System.out.println("data before save when save user controoler : ----------> " + userById);

        userServices.updateUser(userById);
        System.out.println("data after save when save user controoler : ----------> " + userById);

        httpSession.setAttribute("message",
                com.smartcontactmanager.Helper.Message.builder()
                        .content("User updated successfully!")
                        .type(MessageType.green).build());

        return "redirect:/user/profile/update/" + id;
    }

    @GetMapping("/feedback")
    public String feedBackForm() {
        return "user/feed_back";
    }

}