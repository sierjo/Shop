package com.diplom_proj.shop.repository;

import com.diplom_proj.shop.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Integer> {
    // Поиск Юзера по Майлу
    Optional<Users> findByEmail(String email);
    Optional<Users> findByUserId(int id);
}
