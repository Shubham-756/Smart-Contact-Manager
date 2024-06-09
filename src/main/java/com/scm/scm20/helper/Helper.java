package com.scm.scm20.helper;

import java.security.Principal;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;


public class Helper {

    public static String getEmailLoggedinUser(Authentication authentication){
        // agar email is password se login kiya h : email kaise nikale 

        if (authentication instanceof OAuth2AuthenticationToken) {
            
            var auth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
            var clientId = auth2AuthenticationToken.getAuthorizedClientRegistrationId();
            var oauth2User = (OAuth2User)authentication.getPrincipal();
            var username = "";

            // sign in google 
            if(clientId.equalsIgnoreCase("google")){
                System.out.println("Getting email from google");
                username = oauth2User.getAttribute("email").toString();
            }
            // sign in github
            else if(clientId.equalsIgnoreCase("github")){
                System.out.println("Getting email form github");
                username = oauth2User.getAttribute("email") !=null ? oauth2User.getAttribute("email").toString() :
                oauth2User.getAttribute("login").toString()+"@gmail.com";
            }   
            return username;      
        }
        else{
            System.out.println("Getting data from local database");
            return authentication.getName();
        }
    }

}
