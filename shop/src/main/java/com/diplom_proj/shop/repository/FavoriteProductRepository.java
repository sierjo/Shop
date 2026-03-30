package com.diplom_proj.shop.repository;

import com.diplom_proj.shop.entity.FavoriteProducts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteProductRepository extends JpaRepository<FavoriteProducts,Integer> {
    // existsByProducts_ProductId --> Существует ли в избранном обЪект Product с указанным id
    //existsByProducts_ProductId --> Existing or not the Product Object with the specify ID
    boolean existsByProducts_ProductId(Integer productId);


}
