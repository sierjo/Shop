package com.diplom_proj.shop.repository;

import com.diplom_proj.shop.entity.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;
public interface OrderItemRepository extends JpaRepository<OrderItems, Integer> {
}
