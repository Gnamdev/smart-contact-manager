package com.smartcontactmanager.Form;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ContactFormData {

    // @NotEmpty(message = "Please provide your first name")
    // @Size(min = 3, message = "First name should have at least 3 characters")
    private String name;

    // @NotBlank(message = "Email is mandatory")
    // @Email(message = "Email should be valid")
    private String email;

    // @NotBlank(message = "Phone number is mandatory")
    // @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be 10 digits")
    private String phoneNumber;

    // @NotBlank(message = "Address is mandatory")
    // @Size(max = 250, message = "Address must not exceed 250 characters")
    private String address;
    private String description;
    private boolean favorite;
    private String websiteLink;
    private String linkedInLink;

    private MultipartFile contactImage;

}
