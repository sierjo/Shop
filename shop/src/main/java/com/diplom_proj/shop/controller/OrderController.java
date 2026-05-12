package com.diplom_proj.shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
@Controller
public class OrderController {
    @PostMapping("/order/submit")
    public String submitOrderForm() {

        return "redirect:/order/success";
    }
}
