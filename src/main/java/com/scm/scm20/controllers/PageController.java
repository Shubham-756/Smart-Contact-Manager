package com.scm.scm20.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.scm.scm20.entities.User;
import com.scm.scm20.forms.UserForm;
import com.scm.scm20.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;




@Controller
public class PageController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String index(){
        return "redirect:/home";
    }

    @RequestMapping("/home")
    public String home(Model m)
    {
        m.addAttribute("name", "shubham");
        System.out.println("Home page handler");
        return "home";
    }

    // about route

    @RequestMapping("/about")
    public String aboutPage() {
        return "about";
    }

    //services
    @RequestMapping("/services")
    public String servicesPage() {
        return "services";
    }
    // contact
    @GetMapping("/contact")
    public String contact() {
        return new String("contact");
    }
    
    // login
    @GetMapping("/login")
    public String login() {
        return new String("login");
    }


    // signup
    @GetMapping("/register")
    public String getMethodName(Model model) {
        UserForm userForm = new UserForm();
        // userForm.setName("Shubham");
        // userForm.setAbout("this is form about");
        model.addAttribute("userForm", userForm);

        return "register";
    }

    // processing register
    @PostMapping("/do-register")
    public String processRegister(@Valid @ModelAttribute UserForm userForm, BindingResult rbinding , HttpSession session){
        System.out.println("processing Register");
        // fetch form data 
        // user form
        System.out.println(userForm);
        // validate form data ?
        if(rbinding.hasErrors()){
            return "register";
        }
        // save the database

        // User user = User.builder()
        // .name(userForm.getName())
        // .email(userForm.getEmail())
        // .password(userForm.getPassword())
        // .about(userForm.getAbout())
        // .phoneNumber(userForm.getPhoneNumber())
        // .profilePic("https://static.vecteezy.com/system/resources/thumbnails/020/765/399/small/default-profile-account-unknown-icon-black-silhouette-free-vector.jpg")
        // .build();

        User user = new User();
        user.setName(userForm.getName());
        user.setEmail(userForm.getEmail());
        user.setPassword(userForm.getPassword());
        user.setAbout(userForm.getAbout());
        user.setPhoneNumber(userForm.getPhoneNumber());
        user.setProfilePic("https://static.vecteezy.com/system/resources/thumbnails/020/765/399/small/default-profile-account-unknown-icon-black-silhouette-free-vector.jpg");

        User saveUser = userService.saveUser(user);
        System.out.println("Saved User : ");

        // message="Registration Secessfully"

        session.setAttribute("message","Registration Successful");

        // redirect to login
        return "redirect:/register";
    }
    
    
    

}
