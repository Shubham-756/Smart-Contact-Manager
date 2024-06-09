package com.scm.scm20.services.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.scm.scm20.repsitories.UserRepo;

@Service
public class SecurityCustomUserDetail implements UserDetailsService{

    @Autowired
    UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // apne user ko log karna h 
        return userRepo.findByEmail(username).orElseThrow(()-> new UsernameNotFoundException("User not found with email "+username));
    }



}
