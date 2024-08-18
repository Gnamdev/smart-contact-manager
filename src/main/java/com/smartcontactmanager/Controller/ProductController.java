package com.smartcontactmanager.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.smartcontactmanager.Entity.Product;
import com.smartcontactmanager.Entity.User;
import com.smartcontactmanager.Helper.CurrentUser;
import com.smartcontactmanager.Helper.Helper;
import com.smartcontactmanager.Helper.MessageType;
import com.smartcontactmanager.services.ProductService;
import com.smartcontactmanager.services.UserServices;

import jakarta.servlet.http.HttpSession;

import java.util.List;

// @Controller
// @RequestMapping("/import")
// public class HController {

//     @Autowired

//     private ProductService productService;

//     @PostMapping("/contacts")
//     public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file, Authentication authentication) {

//         logger.info("Type of file is =========================================" + file.getContentType());

//         if (HelperExl.checkExcelFormat(file)) {

//             this.productService.save(file, authentication);

//         }
//         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please upload excel file ");
//     }

//     @GetMapping("/getAllContacts")
//     public List<ContactTableData> getAllProduct() {
//         return this.productService.getAllProducts();
//     }

// }

import java.util.Map;

@Controller
@RequestMapping("/import")
public class ProductController {

    Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @Autowired
    private UserServices userServices;

    @PostMapping("/contacts")
    public String upload(@RequestParam("file") MultipartFile file, Authentication authentication,
            HttpSession httpSession) {

        if (Helper.checkExcelFormat(file)) {
            // true

            this.productService.save(file, authentication);

            httpSession.setAttribute("message",
                    com.smartcontactmanager.Helper.Message.builder()
                            .content("Contact uploaded successFully !!")
                            .type(MessageType.green).build());

            return "user/contacts";

        }
        httpSession.setAttribute("message",
                com.smartcontactmanager.Helper.Message.builder()
                        .content("Please upload excel file!!")
                        .type(MessageType.red).build());

        return "user/contacts";
    }

    @GetMapping("/product")
    public List<Product> getAllProduct() {
        return this.productService.getAllProducts();
    }

}