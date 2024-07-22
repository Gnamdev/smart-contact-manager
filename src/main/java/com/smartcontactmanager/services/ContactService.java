package com.smartcontactmanager.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.smartcontactmanager.Entity.Contact;

@Service
public interface ContactService {
    // save contacts
    Contact save(Contact contact);

    // update contact
    Contact update(Contact contact);

    // get contacts
    List<Contact> getAll();

    // get contact by id

    Contact getById(int id);

    // delete contact

    void delete(int id);

    // search contact
    // Page<Contact> searchByName(String nameKeyword, int size, int page, String
    // sortBy, String order, User user);
    //
    // Page<Contact> searchByEmail(String emailKeyword, int size, int page, String
    // sortBy, String order, User user);
    //
    // Page<Contact> searchByPhoneNumber(String phoneNumberKeyword, int size, int
    // page, String sortBy, String order,
    // User user);

    // get contacts by userId
    List<Contact> getByUserId(int userId);

    // Page<Contact> getByUser(User user, int page, int size, String sortField,
    // String sortDirection);

}
