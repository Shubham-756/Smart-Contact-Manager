package com.scm.scm20.services;



import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.scm.scm20.entities.Contact;
import com.scm.scm20.entities.User;

public interface ConatctService {

    Contact save(Contact contact);
    Contact update(Contact contact);
    List<Contact> getAll();
    Contact getById(String id);
    void delete(String id);
    List<Contact> search(String name, String email, String phoneNumber);
    List<Contact> getByUserId(String userId);
    Page<Contact> getByUser(User user, int page, int size, String sortBy, String direction);

}
