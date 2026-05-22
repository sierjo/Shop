package com.diplom_proj.shop.controller;

import com.diplom_proj.shop.dto.DeliveryOrdersDTO;
import com.diplom_proj.shop.dto.DeliveryPositionOrdersDTO;
import com.diplom_proj.shop.dto.UsersDTO;
import com.diplom_proj.shop.services.SellersService;
import com.diplom_proj.shop.services.UsersService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class SellersController {
    private final SellersService sellersService;
    private final UsersService usersService;

    public SellersController(SellersService sellersService, UsersService usersService) {
        this.sellersService = sellersService;
        this.usersService = usersService;
    }

    @GetMapping("/seller/home")
    public String SellerHome(Model model) {
        UsersDTO user = usersService.dtouser();
        model.addAttribute("currentUser", user);
        return "sellerHomePage";
    }

    @PostMapping("/seller/search")
    @ResponseBody
    public List<DeliveryOrdersDTO> searchOrders(@RequestParam("findValue") String findValue) {
        return sellersService.searchOrdersByName(findValue);
    }

    @PostMapping("/seller/order/details")
    @ResponseBody
    public List<DeliveryPositionOrdersDTO> ModalPanel(@RequestParam("itemId") Integer orderId) {
        // Поиск всех товаров в ордере
        return sellersService.getItemsForModalPanel(orderId);
    }

    @PostMapping("/seller/clientPicksUp")
    @ResponseBody
    public ResponseEntity<Void> ModalPanelPicksUp(@RequestParam("orderId") Integer orderId) {

        if (sellersService.getPickUpStatus(orderId)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/seller/clientRefund")
    @ResponseBody
    public ResponseEntity<Void> ModalPanelRefund(@RequestParam("orderId") Integer orderId) {

        if (sellersService.getRefundStatus(orderId)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }
}