package com.diplom_proj.shop.controller;

import com.diplom_proj.shop.dto.DeliveryOrdersDTO;
import com.diplom_proj.shop.services.SellersService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class SellersController {
    private final SellersService sellersService;

    public SellersController(SellersService sellersService) {
        this.sellersService = sellersService;
    }

    @GetMapping("/seller/home")
    public String SellerHome() {
        return "sellerHomePage";
    }

    @PostMapping("/seller/search")
    @ResponseBody
    public List<DeliveryOrdersDTO> searchOrders(@RequestParam("findValue") String findValue) {
        return sellersService.searchOrdersByName(findValue);
    }
}