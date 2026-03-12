package com.diplom_proj.shop.controller;

import com.diplom_proj.shop.entity.Products;
import com.diplom_proj.shop.services.ProductsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class StronaController {

    private final ProductsService productsService;

    public StronaController(ProductsService productsService) {
        this.productsService = productsService;
    }

    @GetMapping("/strona")
    public String shopPage(Model model) {
        // Get list Products
        List<Products> allProducts = productsService.getAll();

        // Put this list to model by name "products"
        model.addAttribute("products", allProducts);
        return "strona";
    }

}
