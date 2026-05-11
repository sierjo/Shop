package com.diplom_proj.shop.controller;

import com.diplom_proj.shop.dto.ProductDTO;
import com.diplom_proj.shop.dto.UsersDTO;
import com.diplom_proj.shop.entity.Products;
import com.diplom_proj.shop.services.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Optional;

@Controller
public class CartItemsController {
    private final UsersService usersService;
    private final OrderServices orderServices;
    private final CartItemsServices cartItemsServices;
    private final ProductsService productsService;

    public CartItemsController(UsersService usersService, OrderServices orderServices, CartItemsServices cartItemsServices, ProductsService productsService) {
        this.usersService = usersService;
        this.orderServices = orderServices;
        this.cartItemsServices = cartItemsServices;
        this.productsService = productsService;
    }


    @GetMapping("/strona/cart")
    public String product_in_cart(Model model) {
        List<ProductDTO> cartItemProduct = cartItemsServices.getAllUsersProductInCart();
        ProductDTO totalPrice = cartItemsServices.dtoProdPrice();

        UsersDTO user = usersService.dtouser();
        model.addAttribute("currentUser", user);
        model.addAttribute("cartItemProduct", cartItemProduct);
        model.addAttribute("totalPrice", totalPrice.getSumAllProductPrice());

        return "cartItemProduct";
    }

    @PostMapping("/cartProduct/delete") // Сделали URL более чистым
    @ResponseBody
    public ResponseEntity<?> deleteCartProduct(@RequestParam Integer productId) {

        // 1. Проверяем, существует ли вообще такой товар в базе магазина
        Optional<Products> optionalProduct = productsService.getProduct(productId);

        if (optionalProduct.isPresent()) {
            // Вызыв метода удаления (он должен возвращать true, если запись удалена)
            boolean isDeleted = cartItemsServices.deleteProductFromCartItem(productId);

            if (isDeleted) {
                // 200 OK - Сервер успешно удалил запись.
                // Получив этот статус, JavaScript мгновенно удалит карточку с экрана!
                return ResponseEntity.ok().build();
            } else {
                // Если метод вернул false (например, такого товара и так не было в избранном у юзера)
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 Not Found
            }
        }

        // 400 Bad Request (Товара с таким ID вообще не существует в природе)
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/cart/update")
    @ResponseBody
    public ResponseEntity<?> updateQuantityProduct(@RequestParam Integer itemId, @RequestParam Integer quantity) {

        // Защита отрицательного числа
        if (quantity <= 0) {
            return ResponseEntity.badRequest().body("Недопустимое количество"); // 400

        } else if (cartItemsServices.updateProductQuantity(itemId, quantity)) {
            ProductDTO newTotalDto = orderServices.dtoProdPrice();
            Integer newTotalSum = newTotalDto.getSumAllProductPrice();
            return ResponseEntity.ok(newTotalSum);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 Not Found
    }
}

