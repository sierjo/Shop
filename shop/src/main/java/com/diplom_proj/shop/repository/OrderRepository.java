package com.diplom_proj.shop.repository;

import com.diplom_proj.shop.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Orders, Integer> {

    List<Orders> findByOrderStatus(String status);
}

