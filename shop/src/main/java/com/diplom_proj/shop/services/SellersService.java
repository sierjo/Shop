package com.diplom_proj.shop.services;

import com.diplom_proj.shop.entity.Orders;
import com.diplom_proj.shop.repository.SellersRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SellersService {
private final SellersRepository sellersRepository;

    public SellersService(SellersRepository sellersRepository) {
        this.sellersRepository = sellersRepository;
    }

    public List<Orders> getAllOrders(){
        return sellersRepository.getAllByOrderStatus("DONE");
    }
}
