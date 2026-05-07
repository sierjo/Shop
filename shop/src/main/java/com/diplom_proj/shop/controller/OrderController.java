package com.diplom_proj.shop.controller;

import com.diplom_proj.shop.dto.ProductDTO;
import com.diplom_proj.shop.services.OrderServices;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class OrderController {
    private final OrderServices orderServices;

    public OrderController(OrderServices orderServices) {
        this.orderServices = orderServices;
    }

    @GetMapping("/create/order")
    public String makingTheOrder(Model model) {

        // Получение итоговой суммы
        ProductDTO totalPrice = orderServices.dtoProdPrice();

        // Передаем сумму в шаблон
        model.addAttribute("totalPrice", totalPrice.getSumAllProductPrice());

        return "makingOrder";
    }

    @PostMapping("/order/submit")
    public String saveAddressAndProceedToPayment(
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String city,
            @RequestParam String street,
            @RequestParam String buildingNo,
            @RequestParam(required = false, defaultValue = "") String aptNo,
            @RequestParam String zipCode
    ) {
        // Передача данных на сервис
        orderServices.saveUserAddress(firstName, lastName, city, street, buildingNo, aptNo, zipCode);

        // Перенаправляем на следующий шаг (например, оплату)
//        return "redirect:/order/payment-mock";
        return "strona";
    }
}
