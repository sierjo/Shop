package com.diplom_proj.shop.controller;

import com.diplom_proj.shop.dto.UsersDTO;
import com.diplom_proj.shop.entity.Products;
import com.diplom_proj.shop.services.FavoriteProductsServices;
import com.diplom_proj.shop.services.ProductsService;
import com.diplom_proj.shop.services.UsersService;
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
public class FavoriteProductController {
    private final UsersService usersService;
    private final FavoriteProductsServices favoriteProductsServices;
    private final ProductsService productsService;

    public FavoriteProductController(UsersService usersService, FavoriteProductsServices favoriteProductsServices, ProductsService productsService) {
        this.usersService = usersService;
        this.favoriteProductsServices = favoriteProductsServices;
        this.productsService = productsService;
    }

    @GetMapping("/products/favoriteProduct")
    public String favorite_product_Page( Model model){
        List<Products> favoriteProd=usersService.getAllUsersProduct();
        UsersDTO user = usersService.dtouser();
        model.addAttribute("currentUser", user);
        model.addAttribute("favoriteProd", favoriteProd);
        return "favoriteProduct";
    }


    @GetMapping("/products/favoriteProduct/exist")
    @ResponseBody
    public ResponseEntity<List<Integer>> favoriteProduct() {

        List<Integer> favoriteIds = usersService.getAllFUsersProduct();

        if (favoriteIds.isEmpty()) {

            // 409 Conflict (Этот товар уже был добавлен в избранное)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        } else {
            return ResponseEntity.ok(favoriteIds); // 200 OK
        }
//        // 400 Bad Request (Товара с таким ID не существует)
//        return ResponseEntity.badRequest().build();
    }


    @PostMapping("/strona/favorites/delete") // Сделали URL более чистым
    @ResponseBody
    public ResponseEntity<?> deleteFavoriteProduct(@RequestParam Integer productId) {

        // 1. Проверяем, существует ли вообще такой товар в базе магазина
        Optional<Products> optionalProduct = productsService.getProduct(productId);

        if (optionalProduct.isPresent()) {

            // 2. Сразу вызываем метод удаления (он должен возвращать true, если запись удалена)
            boolean isDeleted = favoriteProductsServices.deleteFavoriteUserProduct(productId);

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
}
