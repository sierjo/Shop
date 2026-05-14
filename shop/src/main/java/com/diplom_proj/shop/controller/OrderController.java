package com.diplom_proj.shop.controller;

import com.diplom_proj.shop.dto.OrderRequestDTO;
import com.diplom_proj.shop.dto.ProductDTO;
import com.diplom_proj.shop.entity.Address;
import com.diplom_proj.shop.services.AddressServices;
import com.diplom_proj.shop.services.CartItemsServices;
import com.diplom_proj.shop.services.OrderServices;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class OrderController {
    private final OrderServices orderServices;
    private final CartItemsServices cartItemsServices;
    private final AddressServices addressServices;

    public OrderController(OrderServices orderServices, CartItemsServices cartItemsServices,
                           AddressServices addressServices) {
        this.orderServices = orderServices;
        this.cartItemsServices = cartItemsServices;
        this.addressServices = addressServices;
    }

    @GetMapping("/create/order")
    public String makingTheOrder(Model model) {
        // Получение итоговой суммы
        ProductDTO totalPrice = cartItemsServices.dtoProdPrice();
        // Передача итоговой суммы заказа на страницу
        model.addAttribute("totalPrice", totalPrice.getSumAllProductPrice());

        // Получение списка адресов пользоавтеля
        List<Address> userAddresses = addressServices.getUserAddressList();
        model.addAttribute("userAddresses", userAddresses);
        return "makingOrder";
    }

    @PostMapping("/order/submit")
    public String submitOrderForm(@ModelAttribute OrderRequestDTO orderRequestDTO) {
        orderServices.createOrder(orderRequestDTO);
        // Перенаправление обратно на страницу магазина
        return "redirect:/strona";
    }
}
