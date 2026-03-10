package com.diplom_proj.shop.repository;

import com.diplom_proj.shop.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductsRepository extends JpaRepository<Products, Integer> {
    Optional<Products> findByProductName(String name);
}
