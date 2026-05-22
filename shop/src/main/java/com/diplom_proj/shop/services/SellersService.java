package com.diplom_proj.shop.services;

import com.diplom_proj.shop.dto.DeliveryOrdersDTO;
import com.diplom_proj.shop.dto.DeliveryPositionOrdersDTO;
import com.diplom_proj.shop.entity.Orders;
import com.diplom_proj.shop.repository.OrderRepository;
import com.diplom_proj.shop.repository.SellersRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SellersService {
private final SellersRepository sellersRepository;
private final OrderRepository orderRepository;
    public SellersService(SellersRepository sellersRepository, OrderRepository orderRepository) {
        this.sellersRepository = sellersRepository;
        this.orderRepository = orderRepository;
    }

//    public List<DeliveryOrdersDTO> getAllOrders(){
//        return sellersRepository.getAllByOrderStatus("DONE");
//    }


    public List<DeliveryOrdersDTO> searchOrdersByName(String findValue) {
        return sellersRepository.searchOrdersByName(findValue);
    }


    public List<DeliveryPositionOrdersDTO> getItemsForModalPanel(int orderId) {
        return sellersRepository.getOrderItems(orderId);
    }

    public boolean getPickUpStatus(int id){
        Orders newStatus = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("The orders doesn't find"));
        newStatus.setOrderStatus("PickUp");
        orderRepository.save(newStatus);
        return true;

    }
    public boolean getRefundStatus(int id){
        Orders newStatus = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("The orders doesn't find"));
        newStatus.setOrderStatus("Refund");
        orderRepository.save(newStatus);
        return true;

    }
}
