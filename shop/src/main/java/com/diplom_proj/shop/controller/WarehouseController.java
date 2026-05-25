package com.diplom_proj.shop.controller;

import com.diplom_proj.shop.dto.AssemblingOrdersDTO;
import com.diplom_proj.shop.dto.ModalWindowDTO;
import com.diplom_proj.shop.dto.UsersDTO;
import com.diplom_proj.shop.entity.Products;
import com.diplom_proj.shop.services.UsersService;
import com.diplom_proj.shop.services.WarehouseService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class WarehouseController {
    private final WarehouseService warehouseService;
    private final UsersService usersService;

    public WarehouseController(WarehouseService warehouseService, UsersService usersService) {
        this.warehouseService = warehouseService;
        this.usersService = usersService;
    }

    @GetMapping("/products")
    public String Product(Model model) {
        model.addAttribute("product", new Products());
        return "products";
    }

    @GetMapping("/order/assembling")
    public String Collection(Model model) {
        List<AssemblingOrdersDTO> allAssemblingOrders = warehouseService.getAllOrders();
        UsersDTO user = usersService.dtouser();
        model.addAttribute("currentUser", user);
        model.addAttribute("order", allAssemblingOrders);
        return "assemblingOrders";
    }

    @PostMapping("/order/modalPanel")
    @ResponseBody
    public List<ModalWindowDTO> ModalPanel(@RequestParam("itemId") Integer orderId) {
        // Поиск всех товаров связанных с этим ордером
        return warehouseService.getItemsForModalPanel(orderId);
    }

    @PostMapping("/order/assembling/complete")
    @ResponseBody
    public ResponseEntity<?> OrderCollectionComplete(@RequestParam Integer orderId) {
        if (warehouseService.completeCollectOrder(orderId)) {
            return ResponseEntity.ok().build();
        }
        // Ошибка
        return ResponseEntity.badRequest().build();
    }
}
