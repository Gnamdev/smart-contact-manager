package com.smartcontactmanager.Controller;

import com.nimbusds.oauth2.sdk.Message;
import com.smartcontactmanager.Entity.Contact;
import com.smartcontactmanager.Entity.User;
import com.smartcontactmanager.Form.ContactFormData;
import com.smartcontactmanager.Helper.CurrentUser;
import com.smartcontactmanager.Helper.MessageType;
import com.smartcontactmanager.services.ContactService;
import com.smartcontactmanager.services.ImageService;
import com.smartcontactmanager.services.UserServices;

import jakarta.validation.Valid;

import java.util.UUID;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user/contacts")
public class ContactControler {

    @Autowired
    private ContactService contactService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private UserServices userServices;

    private Logger logger = org.slf4j.LoggerFactory.getLogger(ContactControler.class);

    @RequestMapping("/add")
    public String addContactView(Model model) {
        System.out.println("Contact added");

        model.addAttribute("contactForm", new ContactFormData());

        return "user/add_contact";
    }

    @RequestMapping(value = "/processForm", method = RequestMethod.POST)
    public String processContactForm(@ModelAttribute ContactFormData contactFormData,
            HttpSession session,
            Authentication authentication, @RequestParam("contactImage") MultipartFile multipartFile) {

        // if (result.hasErrors()) {

        // session.setAttribute("message",
        // com.smartcontactmanager.Helper.Message.builder()
        // .content("Invalid inforation..")
        // .type(MessageType.red).build());

        // return "user/add_contact";
        // }

        logger.info(contactFormData.getContactImage().getOriginalFilename());

        String filename = UUID.randomUUID().toString();

        String fileUrl = imageService.uploadImage(multipartFile, filename);

        String username = CurrentUser.getEmailWithLoggedUser(authentication);
        // form ---> contact

        User user = userServices.getUserByEmail(username);
        // 2 process the contact picture

        Contact content = new Contact();

        content.setName(contactFormData.getName());
        content.setAddress(contactFormData.getAddress());
        content.setDescription(contactFormData.getDescription());

        content.setEmail(contactFormData.getEmail());
        content.setLinkedInLink(contactFormData.getLinkedInLink());
        content.setPicture(fileUrl);
        content.setFavorite(contactFormData.isFavorite());
        content.setPhoneNumber(contactFormData.getPhoneNumber());
        content.setWebsiteLink(contactFormData.getWebsiteLink());
        content.setCloudinaryImagePublicId(filename);

        content.setUser(user);

        contactService.save(content);

        // if (contactFormData.getContactImage() != null &&
        // !contactFormData.getContactImage().isEmpty()) {
        // String filename = UUID.randomUUID().toString();
        // String fileURL = imageService.uploadImage(contactFormData.getContactImage(),
        // filename);
        // content.setPicture(fileURL);
        // content.setCloudinaryImagePublicId(filename);

        // }

        com.smartcontactmanager.Helper.Message mgs = com.smartcontactmanager.Helper.Message.builder()
                .content("Registered Successfully")
                .type(MessageType.green)
                .build();
        session.setAttribute("message", mgs);

        return "redirect:/user/contacts/add";
    }
}
