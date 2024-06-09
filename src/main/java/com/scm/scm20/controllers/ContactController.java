package com.scm.scm20.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.scm.scm20.entities.Contact;
import com.scm.scm20.entities.User;
import com.scm.scm20.forms.ContactForm;
import com.scm.scm20.helper.Helper;
import com.scm.scm20.services.ConatctService;
import com.scm.scm20.services.ImageService;
import com.scm.scm20.services.UserService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/user/contacts")
public class ContactController {

    @Autowired
    private ImageService imageService;

    private Logger logger = LoggerFactory.getLogger(ContactController.class);

    @Autowired
    private ConatctService conatctService;

    @Autowired
    private UserService userService;

    @RequestMapping("/add")
    public String addContactView(Model model) {
        ContactForm contactForm = new ContactForm();

        model.addAttribute("contactForm", contactForm);
        return "user/add_contact";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String saveContact(@Valid @ModelAttribute ContactForm contactForm, BindingResult result,
            Authentication authentication) {
        // process the form data
        String username = Helper.getEmailLoggedinUser(authentication);
        if (result.hasErrors()) {
            return "user/add_contact";
        }

        // form -----> contact
        User user = userService.getUserByEmail(username);

        // upload krne ka code

        String fileURL = imageService.uploadImage(contactForm.getContactImage());

        Contact contact = new Contact();
        contact.setName(contactForm.getName());
        contact.setFavorate(contactForm.isFavorate());
        contact.setEmail(contactForm.getEmail());
        contact.setPhoneNumber(contactForm.getPhoneNumber());
        contact.setAddress(contactForm.getAddress());
        contact.setDescription(contactForm.getDescription());
        contact.setUser(user);
        contact.setLinkedInLink(contactForm.getLinkedInLink());
        contact.setWebsiteLink(contactForm.getWebsiteLink());
        contact.setPicture(fileURL);

        conatctService.save(contact);

        System.out.println(contactForm);
        return "redirect:/user/contacts/add";
    }

    // view contact
    @RequestMapping
    public String viewContact(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
            @RequestParam(value = "direction", defaultValue = "asc") String direction,
            Model model, Authentication authentication) {
        String username = Helper.getEmailLoggedinUser(authentication);
        User user = userService.getUserByEmail(username);
        Page<Contact> pageContact = conatctService.getByUser(user,page,size,sortBy,direction);
        model.addAttribute("pageContact", pageContact);

        return "user/contacts";
    }

}
