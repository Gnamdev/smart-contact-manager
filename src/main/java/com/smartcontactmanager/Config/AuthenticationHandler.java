package com.smartcontactmanager.Config;

import com.smartcontactmanager.Entity.User;
import com.smartcontactmanager.Helper.AppConstants;
import com.smartcontactmanager.repositories.UserRepo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class AuthenticationHandler implements AuthenticationSuccessHandler {
    @Autowired
    UserRepo userRepository;

    Logger logger = LoggerFactory.getLogger(AuthenticationHandler.class);

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException {

        OAuth2AuthenticationToken oAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
        logger.info("Authentication success");

        String authorizedClientRegistrationId = oAuth2AuthenticationToken.getAuthorizedClientRegistrationId();

        DefaultOAuth2User principal = (DefaultOAuth2User) authentication.getPrincipal();

        User user = new User();
        user.setPhoneNumber("");
        user.setRolesList(List.of(AppConstants.ROLE_USER));

        user.setActive(true);
        user.setRole(AppConstants.ROLE_USER);
        user.setEmailVerified(true);
        user.setPassword("password");
        user.setEmailVerified(true);

        if (authorizedClientRegistrationId.equalsIgnoreCase("google")) {

            user.setEmail(principal.getAttribute("email").toString());
            // user.setProvide(Providers.GOOGLE);

            user.setFirstName(principal.getAttribute("name").toString());
            // user.setLastName(principal.getAttribute("lastName").toString()
            user.setProviderId(principal.getName());

        } else if (authorizedClientRegistrationId.equalsIgnoreCase("github")) {

            String email = principal.getAttribute("email") != null ? principal.getAttribute("email").toString()
                    : principal.getAttribute("login").toString() + "@github.com";

            String pic = principal.getAttribute("avatar-url");

            String name = principal.getAttribute("login").toString();
            String providerId = principal.getName();
            user.setEmail(email);
            user.setProviderId(providerId);
            user.setFirstName(name);
            // user.setProvide(Providers.GITHUB);
        }

        User aNull = userRepository.findByEmail(user.getEmail()).orElse(null);

        if (aNull == null) {
            userRepository.save(user);
            logger.info("user saved by Custom login " + user.getEmail());
        }

        new DefaultRedirectStrategy().sendRedirect(request, response, "/user/profile");
    }
}
