package com.diplom_proj.shop.services;

import com.diplom_proj.shop.dto.AssemblingOrdersDTO;
import com.diplom_proj.shop.entity.OrderItems;
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
        List<OrderItems> allOrders = orderItemRepository.findAll();

        // пустой список для DTO
        List<AssemblingOrdersDTO> dtoList = new ArrayList<>();

        for (OrderItems item : allOrders) {
            AssemblingOrdersDTO dto = new AssemblingOrdersDTO();

            dto.setQuantityProduct(item.getQuantityInOrder());
            dto.setProductId(item.getProducts().getProductId());
            dto.setOrderId(item.getOrders().getOrderId());
            dto.setCity(item.getOrders().getCity());
            dto.setSumQuantityesProducts(orderItemRepository.getSumOfOrdersItemPrices(item.getOrders().getOrderId()));

            // Добавление заполненного DTO в список
            dtoList.add(dto);
        }
        return dtoList;
    }
}