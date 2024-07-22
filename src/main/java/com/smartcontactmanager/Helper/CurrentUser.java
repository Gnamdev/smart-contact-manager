package com.smartcontactmanager.Helper;

import org.springframework.security.core.Authentication;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

import org.springframework.security.oauth2.core.user.OAuth2User;





public class CurrentUser {



    public static String getEmailWithLoggedUser(Authentication auth) {
//        AuthenticatedPrincipal principal = (AuthenticatedPrincipal) auth.getPrincipal();

        String userEmail = "";

        if(auth instanceof OAuth2AuthenticationToken){
          var  oAuth2AuthenticationToken  =  (OAuth2AuthenticationToken)auth;

            OAuth2User user = (OAuth2User) auth.getPrincipal();
            String authorizedClientRegistrationId = oAuth2AuthenticationToken.getAuthorizedClientRegistrationId();


            if(authorizedClientRegistrationId.equalsIgnoreCase("google")){
                System.out.println("google form");
                userEmail = user.getAttribute("email").toString();

            }
            else if(authorizedClientRegistrationId.equalsIgnoreCase("github")){
                System.out.println("github form");
                 userEmail = user.getAttribute("email") != null ? user.getAttribute("email").toString() : user.getAttribute("login").toString() +"@github.com";

            }
            return userEmail;
        }else{
            System.out.println("custom form");
            return auth.getName();
        }


    }
}
