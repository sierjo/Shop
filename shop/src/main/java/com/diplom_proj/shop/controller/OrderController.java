package com.diplom_proj.shop.controller;

import com.diplom_proj.shop.dto.OrderRequestDTO;
import com.diplom_proj.shop.dto.ProductDTO;
import com.diplom_proj.shop.services.CartItemsServices;
import com.diplom_proj.shop.services.OrderServices;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class OrderController {
    private final OrderServices orderServices;
    private final CartItemsServices cartItemsServices;

    public OrderController(OrderServices orderServices, CartItemsServices cartItemsServices) {
        this.orderServices = orderServices;
        this.cartItemsServices = cartItemsServices;
    }

    @GetMapping("/create/order")
    public String makingTheOrder(Model model) {
        // Получение итоговой суммы
        ProductDTO totalPrice = cartItemsServices.dtoProdPrice();
        // Передача итоговой суммы заказа на страницу
        model.addAttribute("totalPrice", totalPrice.getSumAllProductPrice());
        return "makingOrder";
    }

    @PostMapping("/order/submit")
    public String submitOrderForm(@ModelAttribute OrderRequestDTO orderRequestDTO) {
        orderServices.createOrder(orderRequestDTO);
        // Перенаправление обратно на страницу магазина
        return "redirect:/strona";
    }
}
