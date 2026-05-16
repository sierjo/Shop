package com.diplom_proj.shop.controller;

import com.diplom_proj.shop.dto.AssemblingOrdersDTO;
import com.diplom_proj.shop.dto.ModalWindowDTO;
import com.diplom_proj.shop.entity.Products;
import com.diplom_proj.shop.services.WarehouseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class WarehouseController {
    private final WarehouseService warehouseService;

    public WarehouseController(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    @GetMapping("/products")
    public String Product(Model model) {
        model.addAttribute("product", new Products());
        return "products";
    }

    @GetMapping("/order/assembling")
    public String Collection(Model model) {
        List<AssemblingOrdersDTO> allAssemblingOrders=warehouseService.getAllOrders();
        model.addAttribute("order", allAssemblingOrders);
        return "assemblingOrders";
    }

    @PostMapping("/order/modalPanel")
    @ResponseBody
    public List<ModalWindowDTO> ModalPanel(@RequestParam("itemId") Integer orderId) {
        // Поиск всех товаров связанных с этим ордером
        return warehouseService.getItemsForModalPanel(orderId);
    }
}
