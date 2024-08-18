package com.smartcontactmanager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartcontactmanager.Entity.Product;

public interface ProductRepo extends JpaRepository<Product, String> {
}