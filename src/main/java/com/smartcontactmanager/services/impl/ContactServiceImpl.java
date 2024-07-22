package com.smartcontactmanager.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartcontactmanager.Entity.Contact;
import com.smartcontactmanager.Error.ResourcesNotFoundException;
import com.smartcontactmanager.repositories.ContactRepo;
import com.smartcontactmanager.services.ContactService;

@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactRepo contactRepo;

    @Override
    public Contact save(Contact contact) {
        return contactRepo.save(contact);

    }

    @Override
    public Contact update(Contact contact) {

        var contactOld = contactRepo.findById(contact.getId())
                .orElseThrow(() -> new ResourcesNotFoundException("Contact not found"));
        contactOld.setName(contact.getName());
        contactOld.setEmail(contact.getEmail());
        contactOld.setPhoneNumber(contact.getPhoneNumber());
        contactOld.setAddress(contact.getAddress());
        contactOld.setDescription(contact.getDescription());

        contactOld.setPicture(contact.getPicture());

        contactOld.setFavorite(contact.isFavorite());
        contactOld.setWebsiteLink(contact.getWebsiteLink());
        contactOld.setLinkedInLink(contact.getLinkedInLink());
        contactOld.setCloudinaryImagePublicId(contact.getCloudinaryImagePublicId());

        return contactRepo.save(contactOld);
    }

    @Override
    public List<Contact> getAll() {

        List<Contact> contacts = contactRepo.findAll();
        return contacts;
    }

    @Override
    public Contact getById(int id) {
        return contactRepo.findById(id)
                .orElseThrow(() -> new ResourcesNotFoundException("Contact not found with given id " + id));
    }

    @Override
    public void delete(int id) {
        Contact c = contactRepo.findById(id)
                .orElseThrow(() -> new ResourcesNotFoundException("Contact not found with given id " + id));

        contactRepo.delete(c);
    }

    @Override
    public List<Contact> getByUserId(int id) {
        return contactRepo.findByUserId(id);

    }

}
