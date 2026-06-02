package com.diplom_proj.shop.repository;

import com.diplom_proj.shop.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductsRepository extends JpaRepository<Products, Integer> {
    Optional<Products> findByProductName(String name);

    // Поиск оваров, где или название или описание содержит ключевое слово
    @Query("SELECT p FROM Products p WHERE " +
            "LOWER(p.productName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.productDescription) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Products> searchByKeyword(@Param("keyword") String keyword);
}
