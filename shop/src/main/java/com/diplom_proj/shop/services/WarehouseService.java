package com.diplom_proj.shop.services;

import com.diplom_proj.shop.dto.AssemblingOrdersDTO;
import com.diplom_proj.shop.dto.ModalWindowDTO;
import com.diplom_proj.shop.entity.OrderItems;
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
            // Если ордер помечен как выполненный он не добавляется в список
            if (item.getOrderStatus().equals("DONE")) continue;
            else {
                AssemblingOrdersDTO dto = new AssemblingOrdersDTO();

                dto.setOrderId(item.getOrderId());
                dto.setCity(item.getCity());

                Integer totalItems = orderItemRepository.getSumOfOrdersItemsInOrder(item.getOrderId());
                dto.setSumQuantityesProducts(totalItems);

                dtoList.add(dto);
            }
        }
        return dtoList;
    }

    public List<ModalWindowDTO> getItemsForModalPanel(int orderId) {

        // Все заказы
        List<OrderItems> order = orderItemRepository.findAllByOrders_OrderId(orderId);
        List<ModalWindowDTO> modalDTOOrders = new ArrayList<>();
        for (OrderItems item : order) {
            ModalWindowDTO dtoItem = new ModalWindowDTO();
            dtoItem.setProductId(item.getOrderItemId());
            dtoItem.setProductName(item.getProducts().getProductName());
            dtoItem.setQuantityRequired(item.getQuantityInOrder());
            modalDTOOrders.add(dtoItem);
        }
        return modalDTOOrders;
    }

    public boolean completeCollectOrder(int id) {
        Orders changeStatus = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Товар не найден"));
        changeStatus.setOrderStatus("DONE");
        orderRepository.save(changeStatus);
        return true;
    }
}