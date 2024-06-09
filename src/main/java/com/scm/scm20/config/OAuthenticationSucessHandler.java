package com.scm.scm20.config;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.scm.scm20.entities.Providers;
import com.scm.scm20.entities.User;
import com.scm.scm20.helper.AppConstants;
import com.scm.scm20.repsitories.UserRepo;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuthenticationSucessHandler implements AuthenticationSuccessHandler {

    Logger logger = LoggerFactory.getLogger(OAuthenticationSucessHandler.class);
    @Autowired
    private UserRepo repo;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
                logger.info("OAuthenticationSucessHandler");

                // identify the provider
                 
                var oauth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
                String authorizedClientRegistrationId = oauth2AuthenticationToken.getAuthorizedClientRegistrationId();
                logger.info(authorizedClientRegistrationId);
                
                var oauthUser = (DefaultOAuth2User)authentication.getPrincipal();
                oauthUser.getAttributes().forEach((key,value)->{
                    logger.info(key + " : " +value);
                });

                User user = new User();
                user.setUserId(UUID.randomUUID().toString());
                user.setRoleList(List.of(AppConstants.ROLE_USER));
                user.setEmailVerified(true);
                user.setEnabled(true);
                user.setPassword("dummy");



                if( authorizedClientRegistrationId.equalsIgnoreCase("google")){
                // google 
                    // google attribute
                user.setEmail(oauthUser.getAttribute("email").toString());
                user.setProfilePic(oauthUser.getAttribute("picture").toString());
                user.setName(oauthUser.getAttribute("name").toString());
                user.setProviderUserId(oauthUser.getName());
                user.setProvider(Providers.GOOGLE);
                user.setAbout("This account is created using google");
                }
                else if (authorizedClientRegistrationId.equalsIgnoreCase("github")) {
                // github
                    // github attribute
                String email = oauthUser.getAttribute("email") !=null ? oauthUser.getAttribute("email").toString() :
                oauthUser.getAttribute("login").toString()+"@gmail.com";
                String picture = oauthUser.getAttribute("avatar_url").toString();
                String name = oauthUser.getAttribute("login").toString();
                String prividerUserId = oauthUser.getName();

                user.setEmail(email);
                user.setProfilePic(picture);
                user.setName(name);
                user.setProviderUserId(prividerUserId);
                user.setProvider(Providers.GITHUB);
                user.setAbout("This account is created using github");
                }
                else if (authorizedClientRegistrationId.equalsIgnoreCase("facebook")) {
                    // facebook
                    // facebook attribute
                }

                else{
                    logger.info("Unknowm provider");
                }
                

                /* 
                // data database save
                DefaultOAuth2User user = (DefaultOAuth2User) authentication.getPrincipal();
                // logger.info(user.getName());
                // user.getAttributes().forEach((key,value)->{
                //     logger.info("{}=>{}",key,value);
                // });

                // logger.info(user.getAuthorities().toString());
                
                String email = user.getAttribute("email").toString();
                String name = user.getAttribute("name").toString();
                String picture = user.getAttribute("picture").toString();

                // create user and save in database
                User user1 = new User();
                user1.setEmail(email);
                user1.setName(name);
                user1.setProfilePic(picture);
                user1.setPassword("password");
                user1.setUserId(UUID.randomUUID().toString());
                user1.setProvider(Providers.GOOGLE);
                user1.setEnabled(true);
                user1.setEmailVerified(true);
                user1.setProviderUserId(user.getName());
                user1.setRoleList(List.of(AppConstants.ROLE_USER));
                user1.setAbout("thiis account is created using google");

                User user2 = repo.findByEmail(email).orElse(null);
                if(user2 == null){

                    repo.save(user1);
                    logger.info("User saved"+email);
                }
                */
                
                // save the user 
                User user2 = repo.findByEmail(user.getEmail()).orElse(null);
                if(user2 == null)
                    repo.save(user);
                

                new DefaultRedirectStrategy().sendRedirect(request, response, "/user/profile");
    }



}