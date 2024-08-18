package com.smartcontactmanager.Helper;

import com.smartcontactmanager.Form.ChangePasswordForm;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordsMatchValidator implements ConstraintValidator<PasswordsMatch, ChangePasswordForm> {

    @Override
    public void initialize(PasswordsMatch constraintAnnotation) {
    }

    @Override
    public boolean isValid(ChangePasswordForm changePassword, ConstraintValidatorContext context) {
        if (changePassword == null) {
            return true; // Consider null as valid or use a different approach based on your requirements
        }
        return changePassword.isPasswordsMatching();
    }
}
