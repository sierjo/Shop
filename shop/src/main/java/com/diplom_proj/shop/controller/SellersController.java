package com.diplom_proj.shop.controller;

import com.diplom_proj.shop.dto.DeliveryOrdersDTO;
import com.diplom_proj.shop.services.SellersService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class SellersController {
    private final SellersService sellersService;

    public SellersController(SellersService sellersService) {
        this.sellersService = sellersService;
    }

    @GetMapping("/seller/home")
    public String SellerHome(Model model) {
        List<DeliveryOrdersDTO> allOrders = sellersService.getAllOrders();
        model.addAttribute("orders",allOrders);
        return "sellerHomePage";
    }
}
