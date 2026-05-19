package com.diplom_proj.shop.services;

import com.diplom_proj.shop.dto.DeliveryOrdersDTO;
import com.diplom_proj.shop.repository.SellersRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SellersService {
private final SellersRepository sellersRepository;

    public SellersService(SellersRepository sellersRepository) {
        this.sellersRepository = sellersRepository;
    }

//    public List<DeliveryOrdersDTO> getAllOrders(){
//        return sellersRepository.getAllByOrderStatus("DONE");
//    }


    public List<DeliveryOrdersDTO> searchOrdersByName(String findValue) {
        return sellersRepository.searchOrdersByName(findValue);
    }
}
