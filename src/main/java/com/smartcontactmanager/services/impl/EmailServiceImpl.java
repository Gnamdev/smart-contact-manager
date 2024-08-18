package com.smartcontactmanager.services.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.smartcontactmanager.services.EmailService;

@Service
public class EmailServiceImpl implements EmailService {

    @Value("${spring.mail.properties.domain_name}")
    private String domainName;

    private JavaMailSender javaMailSender;

    public EmailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;

    }

    @Override
    public void sendMail(String to, String subject, String message) {

        // if (!isValidEmail(to)) {
        // System.out.println("Invalid email address: " + to);
        // return;
        // }

        try {

            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setTo(to);
            simpleMailMessage.setSubject(subject);
            simpleMailMessage.setText(message);
            simpleMailMessage.setFrom(domainName);

            javaMailSender.send(simpleMailMessage);

            System.out.println("mail send success ++++++");
        } catch (Exception e) {
            System.out.println("faild to send ..........mail");
            e.printStackTrace();
        }

    }

    // private boolean isValidEmail(String email) {
    // EmailValidator validator = EmailValidator.getInstance();
    // return validator.isValid(email);
    // }

}