package com.diplom_proj.shop.controller;

import com.diplom_proj.shop.entity.Products;
import com.diplom_proj.shop.dto.UsersDTO;
import com.diplom_proj.shop.services.ProductsService;
import com.diplom_proj.shop.services.UsersService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class StronaController {

    private final ProductsService productsService;
    private final UsersService usersService;

    public StronaController(ProductsService productsService, UsersService usersService) {
        this.productsService = productsService;
        this.usersService = usersService;
    }

    @GetMapping("/strona")
    public String shopPage(Model model) {
        // Get list Products
        List<Products> allProducts = productsService.getAll();
        UsersDTO user = usersService.dtouser();
        // Put this list to model by name "products"
        model.addAttribute("currentUser", user);
        model.addAttribute("products", allProducts);
        return "strona";
    }

}
