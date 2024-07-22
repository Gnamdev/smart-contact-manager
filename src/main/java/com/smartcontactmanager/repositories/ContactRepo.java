package com.smartcontactmanager.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.smartcontactmanager.Entity.Contact;

@Repository
public interface ContactRepo extends JpaRepository<Contact, Integer> {

    // Page<Contact> findByUser(User user, Pageable pageable);

    // custom query method
    @Query("SELECT c FROM Contact c WHERE c.user.id = :userId")
    List<Contact> findByUserId(@Param("userId") int userId);

}
