package com.diplom_proj.shop.services;

import com.diplom_proj.shop.dto.AssemblingOrdersDTO;
import com.diplom_proj.shop.entity.Orders;
import com.diplom_proj.shop.repository.OrderItemRepository;
import com.diplom_proj.shop.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WarehouseService {
    public final OrderItemRepository orderItemRepository;
    public final OrderRepository orderRepository;

    public WarehouseService(OrderItemRepository orderItemRepository, OrderRepository orderRepository) {
        this.orderItemRepository = orderItemRepository;
        this.orderRepository = orderRepository;
    }

    public List<AssemblingOrdersDTO> getAllOrders() {

        // Все заказы
        List<Orders> allOrders = orderRepository.findAll();

        // пустой список для DTO
        List<AssemblingOrdersDTO> dtoList = new ArrayList<>();

        for (Orders item : allOrders) {
            AssemblingOrdersDTO dto = new AssemblingOrdersDTO();

            dto.setOrderId(item.getOrderId());
            dto.setCity(item.getCity());

            Integer totalItems = orderItemRepository.getSumOfOrdersItemsInOrder(item.getOrderId());
            dto.setSumQuantityesProducts(totalItems);

            dtoList.add(dto);
        }
        return dtoList;
    }
}