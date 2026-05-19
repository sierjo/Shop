package com.diplom_proj.shop.repository;

import com.diplom_proj.shop.dto.DeliveryOrdersDTO;
import com.diplom_proj.shop.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SellersRepository extends JpaRepository<Orders, Integer> {


//    @Query("SELECT new com.diplom_proj.shop.dto.DeliveryOrdersDTO(" +
//            "o.orderId, " +
//            "CONCAT(o.address.name,' ', o.address.surname), " +
//            "o.city, " +
//            "CAST(o.orderDate AS string), " +
//            "o.totalAmount) " +
//            "FROM Orders o WHERE o.orderStatus = :status")
//    List<DeliveryOrdersDTO> getAllByOrderStatus(@Param("status") String status);

    @Query("SELECT new com.diplom_proj.shop.dto.DeliveryOrdersDTO(" +
            "o.orderId, " +
            "CONCAT(o.address.name, ' ', o.address.surname), " +
            "o.city, " +
            "CAST(o.orderDate AS string), " +
            "o.totalAmount) " +
            "FROM Orders o WHERE o.orderStatus = 'DONE' " +
            "AND UPPER(CONCAT(o.address.name, ' ', o.address.surname)) LIKE UPPER(CONCAT('%', :findValue, '%'))")
    List<DeliveryOrdersDTO> searchOrdersByName(@Param("findValue") String findValue);

}