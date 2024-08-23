package com.smartcontactmanager.Controller;

import com.smartcontactmanager.Entity.Contact;
import com.smartcontactmanager.Entity.User;
import com.smartcontactmanager.Form.ContactFormData;
import com.smartcontactmanager.Form.ContactSearchForm;
import com.smartcontactmanager.Helper.AppConstants;
import com.smartcontactmanager.Helper.CurrentUser;
import com.smartcontactmanager.Helper.Message;
import com.smartcontactmanager.Helper.MessageType;
import com.smartcontactmanager.repositories.ContactRepo;
import com.smartcontactmanager.services.ContactService;
import com.smartcontactmanager.services.ImageService;
import com.smartcontactmanager.services.UserServices;

import jakarta.validation.Valid;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/user/contacts")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @Autowired
    private ContactRepo contactRepo;

    @Autowired
    private ImageService imageService;

    @Autowired
    private UserServices userServices;

    private Logger logger = LoggerFactory.getLogger(ContactController.class);

    @RequestMapping("/add")
    public String addContactView(Model model) {
        model.addAttribute("contactForm", new ContactFormData());
        return "user/add_contact";
    }

    @RequestMapping(value = "/processForm", method = RequestMethod.POST)
    public String processContactForm(@ModelAttribute("contactForm") @Valid ContactFormData contactFormData,

            BindingResult result,
            HttpSession session,
            Authentication authentication, @RequestParam("contactImage") MultipartFile multipartFile) {

        // String filename = UUID.randomUUID().toString();
        String id = UUID.randomUUID().toString();

        Contact byEmail = contactRepo.findByEmail(contactFormData.getEmail());

        if (byEmail != null) {
            Message mgs = Message.builder()
                    .content("This email id already use , please provide another mail id  !")
                    .type(MessageType.red)
                    .build();
            session.setAttribute("message", mgs);

            return "user/add_contact";
        }

        if (result.hasErrors()) {

            logger.info("Form data---------------------: {}", contactFormData);
            logger.info("Has errors------------------------------: {}", result.hasErrors());
            logger.error("Error details------------------------------------------: {}", result.getAllErrors());

            session.setAttribute("message",
                    com.smartcontactmanager.Helper.Message.builder()
                            .content("Invalid information.")
                            .type(MessageType.red).build());

            return "user/add_contact";
        }

        // String defaultImage =
        // "''";

        logger.info("Processing contact image: {}", multipartFile.getOriginalFilename());

        String username = CurrentUser.getEmailWithLoggedUser(authentication);

        User user = userServices.getUserByEmail(username);

        Contact contact = new Contact();
        contact.setId(id);
        contact.setName(contactFormData.getName());

        contact.setUserId(user.getUserId());

        contact.setAddress(contactFormData.getAddress());
        contact.setDescription(contactFormData.getDescription());
        contact.setEmail(contactFormData.getEmail());
        contact.setLinkedInLink(contactFormData.getLinkedInLink());
        ;
        contact.setFavorite(contactFormData.isFavorite());
        contact.setPhoneNumber(contactFormData.getPhoneNumber());

        contact.setUser(user);

        String fileURL;
        String publicId;
        // upload image on cloudanry
        String filename = UUID.randomUUID().toString();

        if (contactFormData.getContactImage() != null && !contactFormData.getContactImage().isEmpty()) {

            fileURL = imageService.uploadImage(multipartFile, filename);
            // contact.setPicture(fileURL);
            publicId = filename;

        } else {
            fileURL = AppConstants.DEFAULT_IMAGE;
            publicId = filename;

        }

        contact.setPicture(fileURL);
        contact.setCloudinaryImagePublicId(publicId);

        contactService.save(contact);

        com.smartcontactmanager.Helper.Message message = com.smartcontactmanager.Helper.Message.builder()
                .content("Contact save Successfully !")
                .type(MessageType.green)
                .build();
        session.setAttribute("message", message);

        return "redirect:/user/contacts/add";
    }

    @RequestMapping
    public String contactsViews(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = AppConstants.PAGE_SIZE + "") int size,
            @RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
            @RequestParam(value = "direction", defaultValue = "asc") String direction,
            Authentication authentication, Model model) {

        String emailWithLoggedUser = CurrentUser.getEmailWithLoggedUser(authentication);

        User userByEmail = userServices.getUserByEmail(emailWithLoggedUser);

        Page<Contact> pageContact = contactService.getByUser(userByEmail, page, size, sortBy, direction);

        model.addAttribute("pageContact", pageContact);
        model.addAttribute("pageSize", AppConstants.PAGE_SIZE);

        model.addAttribute("contactSearchForm", new ContactSearchForm());

        // model.addAttribute("contacts", contacts);
        return "user/contacts";
    }

    @RequestMapping("/search")
    public String searchHander(@ModelAttribute ContactSearchForm contactSearchForm,
            @RequestParam(value = "size", defaultValue = AppConstants.PAGE_SIZE + "") int size,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
            @RequestParam(value = "direction", defaultValue = "asc") String direction,
            Model model,
            Authentication authentication) {

        logger.info("field {} keyword {}", contactSearchForm.getField(), contactSearchForm.getValue());

        var user = userServices.getUserByEmail(CurrentUser.getEmailWithLoggedUser(authentication));

        Page<Contact> pageContact = null;
        if (contactSearchForm.getField().equalsIgnoreCase("name")) {
            pageContact = contactService.searchByName(contactSearchForm.getValue(), size, page, sortBy, direction,
                    user);
        } else if (contactSearchForm.getField().equalsIgnoreCase("email")) {
            pageContact = contactService.searchByEmail(contactSearchForm.getValue(), size, page, sortBy, direction,
                    user);
        } else if (contactSearchForm.getField().equalsIgnoreCase("phone")) {
            pageContact = contactService.searchByPhoneNumber(contactSearchForm.getValue(), size, page, sortBy,
                    direction, user);
        }

        logger.info("pageContact {}", pageContact);

        model.addAttribute("contactSearchForm", contactSearchForm);

        if (pageContact == null) {
            model.addAttribute("errorMessage",
                    "Unable to fetch contact data but you can try by Fields. Please try again later.");
        } else {
            model.addAttribute("pageContact", pageContact);
        }

        // model.addAttribute("pageContact", pageContact);

        model.addAttribute("pageSize", AppConstants.PAGE_SIZE);
        return "user/search";
    }

    // for view

    @RequestMapping("/{id}")
    public String showProfileHandler(@PathVariable String id, Model model) {

        Contact byId = contactService.getById(id);

        if (byId.getLinkedInLink().isEmpty()) {
            byId.setLinkedInLink("--");

        }
        // if (byId.getWebsiteLink().isEmpty()) {
        // byId.setWebsiteLink("--");
        // }

        model.addAttribute("veiwData", byId);

        return "user/viewPage";
    }

    // detete contact
    @RequestMapping("/delete/{contactId}")
    public String deleteContact(
            @PathVariable("contactId") String contactId,
            HttpSession session) {
        contactService.delete(contactId);
        logger.info("contactId {} deleted", contactId);

        return "redirect:/user/contacts";
    }

    // GetMapping
    @RequestMapping("/update/{id}")
    public String profileHandler(@PathVariable String id, Model model, HttpSession session) {

        Contact contact = contactService.getById(id);

        ContactFormData contactForm = new ContactFormData();

        contactForm.setName(contact.getName());
        contactForm.setEmail(contact.getEmail());
        contactForm.setPhoneNumber(contact.getPhoneNumber());
        contactForm.setAddress(contact.getAddress());
        contactForm.setDescription(contact.getDescription());
        contactForm.setFavorite(contact.isFavorite());
        // contactForm.setWebsiteLink(contact.getWebsiteLink());
        contactForm.setLinkedInLink(contact.getLinkedInLink());
        // contactForm.setContactImage(contact.getPicture());

        model.addAttribute("contactForm", contactForm);
        model.addAttribute("contactId", id);
        // session.setAttribute("contactId", contact.getId());

        return "user/update_profile";
    }

    // handelForm

    @PostMapping("/update/{id}")
    public String postMethodName(@Valid @ModelAttribute("contactForm") ContactFormData contactForm,
            BindingResult result,
            @RequestParam("contactId") String formId,
            @PathVariable String id, @RequestParam("contactImage") MultipartFile multipartFile,
            HttpSession httpSession,
            Model model) {

        String i_d = formId != null ? formId : id; // Ensure ID is not null

        if (result.hasErrors()) {
            logger.info("error : {}", result.hasErrors());

            model.addAttribute("userId", i_d);
            httpSession.setAttribute("message",
                    com.smartcontactmanager.Helper.Message.builder()
                            .content("Invalid information. please provide correct information !")
                            .type(MessageType.red).build());
            return "user/update_profile";
        }

        Contact con = contactService.getById(i_d);

        con.setId(id);
        con.setId(id);
        con.setName(contactForm.getName());
        con.setEmail(contactForm.getEmail());
        con.setPhoneNumber(contactForm.getPhoneNumber());
        con.setAddress(contactForm.getAddress());
        con.setDescription(contactForm.getDescription());
        con.setFavorite(contactForm.isFavorite());
        // con.setWebsiteLink(contactForm.getWebsiteLink());
        con.setLinkedInLink(contactForm.getLinkedInLink());

        // process image:

        if (contactForm.getContactImage() != null && !contactForm.getContactImage().isEmpty()) {
            logger.info("file is not empty");
            String fileName = UUID.randomUUID().toString();

            String imageUrl = imageService.uploadImage(multipartFile, fileName);
            con.setCloudinaryImagePublicId(fileName);
            con.setPicture(imageUrl);

        } else {
            logger.info("file is empty");
        }

        var updateCon = contactService.update(con);
        logger.info("updated contact {}", updateCon);

        httpSession.setAttribute("message",
                com.smartcontactmanager.Helper.Message.builder()
                        .content("Contact Update successFully !!")
                        .type(MessageType.green).build());

        return "redirect:/user/contacts/update/" + id;
    }

    // handler to show updatecontact view
    // @RequestMapping("/updateView/{contactId}")
    // public String updateView(@PathVariable("contactId") String contactId, Model
    // model) {

    // var contact = contactService.getById(contactId);

    // // ContactFormDataValidation contactFormData = new
    // ContactFormDataValidation();

    // // contactFormData.setName(contact.getName());
    // // contactFormData.setEmail(contact.getEmail());
    // // contactFormData.setPhoneNumber(contact.getPhoneNumber());
    // // contactFormData.setAddress(contact.getAddress());
    // // contactFormData.setDiscription(contact.getDescription());
    // // contactFormData.setFavourite(contact.isFavorite());
    // // contactFormData.setPicture(contact.getPicture());

    // // model.addAttribute("contactForm", contactFormData);
    // // model.addAttribute("contactId", contactId);

    // return "user/updateFormView";
    // }

    // // handler for updating the contact
    // @RequestMapping(value = "updateContact/{contactId}", method =
    // RequestMethod.POST)
    // public String updateContact(@Valid @ModelAttribute("contactForm")
    // ContactFormData contactFormData,
    // BindingResult result,
    // @PathVariable("contactId") String contactId, Model model, HttpSession
    // session) {

    // // form validation logic
    // // validate form
    // if (result.hasErrors()) {

    // // model.addAttribute("contactForm", contactFormData);

    // logger.info("erorr in updating contact ----->", result.hasFieldErrors());
    // return "user/updateFormView";
    // }

    // var contact = contactService.getById(contactId);

    // contact.setId(contactId);
    // contact.setName(contactFormData.getName());
    // contact.setEmail(contactFormData.getEmail());
    // contact.setPhoneNumber(contactFormData.getPhoneNumber());
    // contact.setAddress(contactFormData.getAddress());
    // contact.setDescription(contactFormData.getDescription());
    // contact.setFavorite(contactFormData.getFavourite());

    // // processing image
    // if (contactFormData.getContactImage() != null &&
    // !contactFormData.getContactImage().isEmpty()) {
    // logger.info("file is not empty");
    // String fileName = UUID.randomUUID().toString();

    // String imageUrl = imageService.uploadImage(contactFormData.getContactImage(),
    // fileName);
    // contact.setCloudinaryImagePublicId(fileName);
    // contact.setPicture(imageUrl);

    // } else {
    // logger.info("file is empty");
    // }

    // var Updatedcon = contactService.update(contact);

    // logger.info("updated contact {}", Updatedcon);

    // session.setAttribute("message",
    // Message.builder().content("Contact Updated Successfully
    // !").type(MessageType.green).build());

    // return "redirect:/user/contacts/updateView/" + contactId;
    // }

}
