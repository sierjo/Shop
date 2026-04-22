package com.diplom_proj.shop.repository;

import com.diplom_proj.shop.entity.Carts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Carts, Integer> {
    Carts getCartsByUser_UserId(Integer userId);

    Carts findCartsByUser_UserId(Integer userId);

    boolean existsCartsByUser_UserId(Integer userId);
}
