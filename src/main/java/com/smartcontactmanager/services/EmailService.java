package com.smartcontactmanager.services;

public interface EmailService {

    public void sendMail(String to, String subject, String message);

    // extra service
    // void sendMail(String to[] , String subject , String message);
    // void sendEmailWithHtml();
    // void sendEmailWithAttachment();

}