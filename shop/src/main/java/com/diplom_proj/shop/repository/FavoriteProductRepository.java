package com.diplom_proj.shop.repository;

import com.diplom_proj.shop.entity.FavoriteProducts;
import com.diplom_proj.shop.entity.Products;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FavoriteProductRepository extends JpaRepository<FavoriteProducts,Integer> {
    // existsByProducts_ProductId --> Существует ли в избранном обЪект Product с указанным id
    //existsByProducts_ProductId --> Existing or not the Product Object with the specify ID
    boolean existsByProducts_ProductId(Integer productId);


    boolean existsByUsers_UserIdAndProducts_ProductId(Integer userId, Integer productId);


    // Получаем ТОЛЬКО список ID товаров (product_id) для конкретного пользователя
//    @Query("SELECT f.products.productId FROM FavoriteProducts f WHERE f.users.userId = :userId")
    @Query("SELECT f.products FROM FavoriteProducts f WHERE f.users.userId = :userId")
    List<Products> findAllByUsers_UserId(@Param("userId") Integer userId);
    @Query("SELECT f.products.productId FROM FavoriteProducts f WHERE f.users.userId = :userId")
    List<Integer> getAllProductsFUdByUsers(@Param("userId") Integer userId);

    @Transactional
    Integer deleteByUsers_UserIdAndProducts_ProductId(Integer userId, Integer productId);
}
