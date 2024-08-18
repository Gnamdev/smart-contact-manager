package com.smartcontactmanager.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.smartcontactmanager.Entity.Contact;
import com.smartcontactmanager.Entity.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface ContactRepo extends JpaRepository<Contact, String> {

    // boolean existsByUserIdAndEmailOrPhoneNumber(User user, String email, String
    // phoneNumber);

    // @Query("SELECT c FROM Contact c WHERE c.userId = :userId")
    // boolean existsByUserIdAndEmailOrPhoneNumber(@Param("userId") String userId,
    // @Param("email") String email,
    // @Param("phoneNumber") String phoneNumber);
    boolean existsByUserIdAndEmailOrPhoneNumber(String userId, String email, String phoneNumber);

    Page<Contact> findByUser(User user, Pageable pageable);

    boolean existsByEmail(String email);

    // custom query method
    @Query("SELECT c FROM Contact c WHERE c.user.id = :userId")
    List<Contact> findByUserId(@Param("userId") String userId);

    @Modifying
    @Query("DELETE FROM Contact c WHERE c.id = :id")
    void deleteContact(@Param("id") String id);

    @Query("SELECT c FROM Contact c WHERE c.user.id = :userId AND c.favorite = true")
    List<Contact> findFavoriteContactsByUserId(@Param("userId") String userId);

    Page<Contact> findByUserAndNameContaining(User user, String namekeyword, Pageable pageable);

    Page<Contact> findByUserAndEmailContaining(User user, String emailkeyword, Pageable pageable);

    Page<Contact> findByUserAndPhoneNumberContaining(User user, String phonekeyword, Pageable pageable);

}
