package com.smartcontactmanager.services.impl;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartcontactmanager.Entity.User;
import com.smartcontactmanager.services.EmailService;
import com.smartcontactmanager.services.ForgotPasswordService;
import com.smartcontactmanager.services.UserServices;

import jakarta.servlet.http.HttpSession;

@Service
public class ForgetPasswordServiceImpl implements ForgotPasswordService {

    private static final int CODE_LENGTH = 4;

    @Autowired
    private EmailService emailService;
    @Autowired
    private UserServices userServices;

    @Override
    public boolean otpSend(String email, HttpSession session) {

        User userByEmail = userServices.getUserByEmail(email);

        if (userByEmail != null) {
            String otp = generateVerificationCode();
            System.out.println("otp is ----------> " + otp);
            session.setAttribute("otp", otp);
            session.setAttribute("otpGeneratedTime", System.currentTimeMillis());
            emailService.sendMail(email, "OTP for forget password request : ", otp);

        } else {
            return false;
        }

        return true;
    }

    @Override
    public boolean otpVerify(String otp, HttpSession session) {

        String sessionOtp = (String) session.getAttribute("otp");

        if (sessionOtp != null && sessionOtp.equals(otp)) {
            // OTP is correct
            // Proceed with user verification or account activation
            session.removeAttribute("otp"); // Optionally remove OTP after successful verification
            return true;
        } else {
            // OTP is incorrect

            return false;
        }

    }

    @Override
    public boolean resetePassword(String email, String newPass, String confirmPass) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'resetePassword'");
    }

    private String generateVerificationCode() {
        Random random = new Random();
        int otp = random.nextInt((int) Math.pow(10, CODE_LENGTH));

        // httpSession.setAttribute("otp", otp);
        return String.format("%04d", otp);
    }

}
