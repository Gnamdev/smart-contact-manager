package com.smartcontactmanager.Form;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Component
public class UserFormData {

    @NotEmpty(message = "Please provide your first name")
    @Size(min = 3, message = "First name should have at least 3 characters")
    private String firstName;

    @NotEmpty(message = "Please provide your last name")
    @Size(min = 3, message = "Last name should have at least 3 characters")
    private String lastName;

    @Email(message = "Please provide a valid email address")
    @NotEmpty(message = "Email is required")
    @Column(unique = true)
    private String email;

    @NotBlank(message = "Phone number is mandatory")
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be 10 digits")
    private String phoneNumber;

    @Size(min = 8, max = 12, message = "Password should be between 8 and 12 characters")
    private String password;
    private String about;

    private MultipartFile userImage;
}
