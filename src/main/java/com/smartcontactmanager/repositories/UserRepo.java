package com.smartcontactmanager.repositories;

import com.smartcontactmanager.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, String> {

   boolean existsByEmail(String email);

   Optional<User> findByEmail(String email);

   Optional<User> findByEmailAndPassword(String email, String password);

   Optional<User> findByEmailToken(String id);

}
