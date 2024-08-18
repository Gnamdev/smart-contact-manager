package com.smartcontactmanager.Controller.UserController;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import com.smartcontactmanager.Entity.User;
import com.smartcontactmanager.Form.ChangePasswordForm;
import com.smartcontactmanager.Helper.Message;
import com.smartcontactmanager.Helper.MessageType;
import com.smartcontactmanager.services.ForgotPasswordService;
import com.smartcontactmanager.services.UserServices;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ForgotPasswordController {
    private static final long RESEND_DELAY_MS = 30000; // 30 seconds

    // forget password

    @Autowired
    private ForgotPasswordService forgotPasswordService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserServices userServices;

    @GetMapping("/forget")
    public String showForgetPasswordPage() {
        return "forgot_password";
    }

    @PostMapping("/forget")
    public String sendOtp(@RequestParam("email") String email, HttpSession session) {

        User realUser = userServices.getUserByEmail(email);

        if (realUser == null) {
            Message message = Message.builder()
                    .content("please provide registerd email only !")
                    .type(MessageType.red)
                    .build();

            session.setAttribute("message", message);
            return "forgot_password";

        }

        if (email.isEmpty()) {
            Message message = Message.builder()
                    .content("please provide email !")
                    .type(MessageType.green)
                    .build();

            session.setAttribute("message", message);
            return "forgot_password";
        }

        boolean otpSend = forgotPasswordService.otpSend(email, session);
        System.out.println("otp --------->  " + otpSend);
        System.out.println("email -------------> " + email);

        if (otpSend) {
            Message message = Message.builder()
                    .content("OTP SEND TO YOUR EMAIL !")
                    .type(MessageType.green)
                    .build();

            session.setAttribute("message", message);

            return "verify_otp";

        } else {
            Message message = Message.builder()
                    .content("OTP Not send , try again later !")
                    .type(MessageType.red)
                    .build();

            session.setAttribute("message", message);
        }
        return "forgot_password";
    }

    @GetMapping("/verify_otp")
    public String showVerifyOtpPage() {
        return "verify_otp";
    }

    @PostMapping("/verify_otp")
    public String handleVerifyOtp(@RequestParam("otp1") String digitOne, @RequestParam("otp2") String digitTwo,
            @RequestParam("otp3") String digitThree, @RequestParam("otp4") String digiFour, HttpSession httpSession) {

        String otp = digitOne + digitTwo + digitThree + digiFour;
        System.out.println("verify otp is : " + otp);

        boolean otpVerify = forgotPasswordService.otpVerify(otp, httpSession);

        if (otpVerify) {
            Message message = Message.builder()
                    .content("OTP is verifed !")
                    .type(MessageType.green)
                    .build();

            httpSession.setAttribute("message", message);

            return "change_password";
        } else {

            Message message = Message.builder()
                    .content("Incorrect Password !")
                    .type(MessageType.red)
                    .build();

            httpSession.setAttribute("message", message);
            return "verify_otp";
        }

    }

    // resend
    @GetMapping("/resendOtp")
    public String resendOtpHandler(HttpSession session) {

        System.out.println("resend opt -------> ");
        Long otpGeneratedTime = (Long) session.getAttribute("otpGeneratedTime");

        if (otpGeneratedTime != null && System.currentTimeMillis() - otpGeneratedTime < RESEND_DELAY_MS) {
            Message message = Message.builder()
                    .content("You can only request a new OTP every 30 seconds.")
                    .type(MessageType.blue)
                    .build();

            session.setAttribute("message", message);
            return "";
        }

        // Generate a new OTP
        String otp = String.format("%04d", new Random().nextInt(9999));

        // Store the new OTP and update the timestamp in the session
        session.setAttribute("otp", otp);
        session.setAttribute("otpGeneratedTime", System.currentTimeMillis());

        forgotPasswordService.otpSend(otp, session);
        Message message = Message.builder()
                .content("OTP SEND IN YOUR EMAIL ")
                .type(MessageType.green)
                .build();

        session.setAttribute("message", message);

        return "verify_otp";
    }

    @RequestMapping(value = "/change_password", method = RequestMethod.GET)
    public String showChangePasswordForm(Model model) {
        model.addAttribute("event", new ChangePasswordForm());
        return "change_password";
    }

    // @RequestMapping(value = "/change_password", method = RequestMethod.POST)
    // public String changePassword(@ModelAttribute("event") @Valid
    // ChangePasswordForm changePassword,
    // BindingResult bindingResult, HttpSession httpSession) {

    // if (bindingResult.hasErrors()) {
    // System.out.println("Error in change password binding result: " +
    // bindingResult.hasFieldErrors());

    // Message message = Message.builder()
    // .content("Please provide correct information!")
    // .type(MessageType.red)
    // .build();

    // httpSession.setAttribute("message", message);
    // return "change_password";
    // }

    // if (!changePassword.isPasswordsMatching()) {
    // Message message = Message.builder()
    // .content("Passwords do not match!")
    // .type(MessageType.red)
    // .build();

    // httpSession.setAttribute("message", message);
    // return "change_password";
    // }

    // // Process the password change
    // System.out.println("Change password: " + changePassword);
    // return "/login"; // Redirect after successful change
    // }

    @RequestMapping(value = "/change_password", method = RequestMethod.POST)
    public String changePassword(
            @RequestParam("email") String email,
            @RequestParam("newPassword") String newPassword,
            @RequestParam("confirmPassword") String confirmPassword,
            HttpSession httpSession) {

        // Validate form fields
        if (email.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
            Message message = Message.builder()
                    .content("Please provide All information!")
                    .type(MessageType.red)
                    .build();
            httpSession.setAttribute("message", message);
            return "change_password";
        }

        if (!newPassword.equals(confirmPassword)) {

            Message message = Message.builder()
                    .content("Passwords do not match!")
                    .type(MessageType.red)
                    .build();

            // httpSession.setAttribute("message", message);
            httpSession.setAttribute("message", message);
            return "change_password";
        }

        // Process the password change
        System.out.println("Email: " + email);
        System.out.println("New Password: " + newPassword);

        User userByEmail = userServices.getUserByEmail(email);

        if (userByEmail != null) {
            userByEmail.setPassword(bCryptPasswordEncoder.encode(newPassword));
            userServices.updateUser(userByEmail);
            Message message = Message.builder()
                    .content("Password Change successfully !")
                    .type(MessageType.green)
                    .build();

            // httpSession.setAttribute("message", message);
            httpSession.setAttribute("message", message);

            return "/login";
        } else {
            Message message = Message.builder()
                    .content("Somthing went wrong  , try again later  !")
                    .type(MessageType.green)
                    .build();

            // httpSession.setAttribute("message", message);
            httpSession.setAttribute("message", message);
            return "redirect:/login";
        }

        // Redirect after successful change
    }
}