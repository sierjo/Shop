package com.diplom_proj.shop.repository;

import com.diplom_proj.shop.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Orders, Integer> {
}

