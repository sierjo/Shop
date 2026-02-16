package com.diplom_proj.shop.repository;

import com.diplom_proj.shop.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersTypeRepository extends JpaRepository<Roles, Integer> {
}
