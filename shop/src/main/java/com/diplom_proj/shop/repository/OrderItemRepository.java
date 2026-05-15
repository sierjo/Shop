package com.diplom_proj.shop.repository;

import com.diplom_proj.shop.entity.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderItemRepository extends JpaRepository<OrderItems, Integer> {
    @Query("SELECT COALESCE(SUM(o.quantityInOrder), 0) FROM OrderItems o WHERE o.orders.orderId = :orderId")
    Integer getSumOfOrdersItemPrices(@Param("order_id") Integer orderId);
}
