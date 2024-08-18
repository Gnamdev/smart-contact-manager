package com.smartcontactmanager.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartcontactmanager.Entity.Contact;
import com.smartcontactmanager.Entity.User;
import com.smartcontactmanager.Error.ResourcesNotFoundException;
import com.smartcontactmanager.repositories.ContactRepo;
import com.smartcontactmanager.services.ContactService;

@Service
@Transactional
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
        contactOld.setLinks(contact.getLinks());

        return contactRepo.save(contactOld);
    }

    @Override
    public List<Contact> getAll() {

        List<Contact> contacts = contactRepo.findAll();
        return contacts;
    }

    @Override
    public Contact getById(String id) {
        return contactRepo.findById(id)
                .orElseThrow(() -> new ResourcesNotFoundException("Contact not found with given id " + id));
    }

    // @Override
    // public void delete(String id) {
    // System.out.println(" contact id for deleting : " + id);

    // Contact c = contactRepo.findById(id)
    // .orElseThrow(() -> new ResourcesNotFoundException(
    // "Contact not found with given id so we can't delete contact ! " + id));

    // System.out.println("contact : " + c.getName());

    // contactRepo.deleteById(id);

    // }
    @Override
    @Transactional
    public void delete(String id) {

        Contact orElseThrow = contactRepo.findById(id)
                .orElseThrow(() -> new ResourcesNotFoundException("contact not found by given id : " + id));
        contactRepo.deleteContact(id);

        // contactRepo.delete(orElseThrow);

        // contactRepo.deleteById(id);
        // var contact = contactRepo.findById(id)
        // .orElseThrow(() -> new ResourcesNotFoundException("Contact not found with
        // given id " + id));
        // contactRepo.delete(contact);

    }

    @Override
    public List<Contact> getByUserId(String id) {
        return contactRepo.findByUserId(id);

    }

    @Override
    public Page<Contact> getByUser(User user, int page, int size, String sortBy, String direction) {

        Sort sort = direction.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

        var pageable = PageRequest.of(page, size, sort);

        return contactRepo.findByUser(user, pageable);

    }

    @Override
    public Page<Contact> searchByName(String nameKeyword, int size, int page, String sortBy, String order, User user) {

        Sort sort = order.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        var pageable = PageRequest.of(page, size, sort);
        return contactRepo.findByUserAndNameContaining(user, nameKeyword, pageable);
    }

    @Override
    public Page<Contact> searchByEmail(String emailKeyword, int size, int page, String sortBy, String order,
            User user) {
        Sort sort = order.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        var pageable = PageRequest.of(page, size, sort);
        return contactRepo.findByUserAndEmailContaining(user, emailKeyword, pageable);
    }

    @Override
    public Page<Contact> searchByPhoneNumber(String phoneNumberKeyword, int size, int page, String sortBy,
            String order, User user) {

        Sort sort = order.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        var pageable = PageRequest.of(page, size, sort);
        return contactRepo.findByUserAndPhoneNumberContaining(user, phoneNumberKeyword, pageable);
    }

    @Override
    public boolean alreadyContact(String email) {
        return contactRepo.existsByEmail(email);
    }

    public interface ContactRepository extends JpaRepository<Contact, String> {
        boolean existsByUserAndEmailOrPhoneNumber(User user, String email, String phoneNumber);
    }

    @Override
    public boolean isContactExistsForUser(String userId, String email, String phoneNumber) {
        return contactRepo.existsByUserIdAndEmailOrPhoneNumber(userId, email, phoneNumber);
    }

}
