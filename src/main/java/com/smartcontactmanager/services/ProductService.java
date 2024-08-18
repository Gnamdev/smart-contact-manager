package com.smartcontactmanager.services;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.core.Authentication;
// import org.springframework.stereotype.Service;
// import org.springframework.web.multipart.MultipartFile;

// import com.smartcontactmanager.Entity.ContactTableData;
// import com.smartcontactmanager.Helper.HelperExl;
// import com.smartcontactmanager.repositories.ProductRepo;

// import java.io.IOException;
// import java.util.List;

// @Service
// public class ProductService {

//     @Autowired
//     private ProductRepo productRepo;

//     @Autowired
//     private HelperExl helperExl;

//     public void save(MultipartFile file, Authentication authentication) {

//         try {
//             List<ContactTableData> contacts = helperExl.convertExcelToListOfProduct(file.getInputStream(),
//                     authentication);

//             this.productRepo.saveAll(contacts);
//         } catch (IOException e) {
//             e.printStackTrace();
//         }

//     }

//     public List<ContactTableData> getAllProducts() {
//         return this.productRepo.findAll();
//     }

// }

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.smartcontactmanager.Entity.Contact;
import com.smartcontactmanager.Entity.Product;
import com.smartcontactmanager.Entity.User;
import com.smartcontactmanager.Helper.CurrentUser;
import com.smartcontactmanager.Helper.Helper;
import com.smartcontactmanager.repositories.ProductRepo;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class ProductService {

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private ContactService contactService;

    @Autowired
    private UserServices userServices;

    public void save(MultipartFile file, Authentication authentication) {

        String emailWithLoggedUser = CurrentUser.getEmailWithLoggedUser(authentication);

        User userByEmail = userServices.getUserByEmail(emailWithLoggedUser);

        try {
            List<Product> products = Helper.convertExcelToListOfProduct(file.getInputStream());
            // this.productRepo.saveAll(products);

            // List<Product> allProduct = productRepo.findAll();

            if (userByEmail == null) {
                throw new RuntimeException("User not found");
            }

            for (Product product : products) {
                System.out.println("user - > " + product);
            }

            // Iterate over each product and create a corresponding contact
            for (Product product : products) {
                Contact contact = new Contact();

                boolean contactExists = contactService.isContactExistsForUser(
                        userByEmail.getUserId(), product.getEmail(), product.getPhoneNumber());

                if (contactExists) {
                    // If the contact already exists, skip saving

                    System.out.println("user id : " + userByEmail.getUserId());
                    // continue;
                }

                contact.setId(UUID.randomUUID().toString());
                contact.setName(product.getName());
                contact.setEmail(product.getEmail());
                contact.setPhoneNumber(product.getPhoneNumber());
                // Set other fields as needed
                contact.setAddress(""); // Example, set actual address if available
                contact.setDescription(""); // Example, set actual description if available
                contact.setFavorite(product.isFavourite()); // Assuming 'favourite' is used as 'favorite'
                contact.setUser(userByEmail); // Associate contact with the user

                // Save the contact to the database
                contactService.save(contact);
            }

            List<Contact> all = contactService.getAll();
            for (Contact contact : all) {
                System.out.println("all contact -- > " + contact);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public List<Product> getAllProducts() {
        return this.productRepo.findAll();
    }

}
