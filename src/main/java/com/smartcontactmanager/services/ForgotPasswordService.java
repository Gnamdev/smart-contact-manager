package com.smartcontactmanager.services;

import jakarta.servlet.http.HttpSession;

public interface ForgotPasswordService {

    // enter mail , send
    boolean otpSend(String email, HttpSession session);

    // verify email , otp submit
    boolean otpVerify(String otp, HttpSession session);

    // resete pass

    boolean resetePassword(HttpSession session);

}