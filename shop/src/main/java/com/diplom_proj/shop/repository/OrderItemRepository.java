package com.diplom_proj.shop.repository;

import com.diplom_proj.shop.entity.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItems, Integer> {
    @Query("SELECT (SUM(o.quantityInOrder)) FROM OrderItems o WHERE o.orders.orderId = :order_id")
    Integer getSumOfOrdersItemsInOrder(@Param("order_id") Integer orderId);
    List<OrderItems> findAllByOrders_OrderId(Integer orderId);
}
