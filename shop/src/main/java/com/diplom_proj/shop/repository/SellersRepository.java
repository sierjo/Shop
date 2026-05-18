package com.diplom_proj.shop.repository;

import com.diplom_proj.shop.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SellersRepository extends JpaRepository<Orders, Integer> {
    List<Orders> getAllByOrderStatus(String status);
}
