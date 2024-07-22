package com.smartcontactmanager.Form;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

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

    @Size(min = 3, message = "Password should be at least 5 characters long")
    // @Pattern(regexp =
    // "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{5,}$", message
    // = "Password must contain at least one digit, one lowercase letter, one
    // uppercase letter, one special character, and no whitespace")
    private String password;

    @Size(min = 8, max = 12, message = "Phone number should be between 8 and 12 characters")
    private String phoneNumber;

    // private String about;
}
