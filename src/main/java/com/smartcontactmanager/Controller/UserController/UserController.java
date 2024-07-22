package com.smartcontactmanager.Controller.UserController;


import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {


    @RequestMapping( "/dashboard")
    public  String userDeshbord(){
        return "user/dashboard";
    }


    @GetMapping("/profile")
    public  String userProfile(Authentication authentication , Model model){

        return "user/profile";
    }
}
