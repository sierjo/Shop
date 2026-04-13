package com.diplom_proj.shop.controller;

import com.diplom_proj.shop.dto.UsersDTO;
import com.diplom_proj.shop.entity.FavoriteProducts;
import com.diplom_proj.shop.entity.Products;
import com.diplom_proj.shop.repository.ProductsRepository;
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
public class StronaController {

    private final ProductsService productsService;
    private final UsersService usersService;
    private final ProductsRepository productsRepository;
    private final FavoriteProductsServices favoriteProductsServices;

    public StronaController(ProductsService productsService, UsersService usersService, ProductsRepository productsRepository, FavoriteProductsServices favoriteProductsServices) {
        this.productsService = productsService;
        this.usersService = usersService;
        this.productsRepository = productsRepository;
        this.favoriteProductsServices = favoriteProductsServices;
    }

    @GetMapping("/strona")
    public String shopPage(Model model) {
        // Get list Products
        List<Products> allProducts = productsService.getAll();
        UsersDTO user = usersService.dtouser();
        // Put this list to model by name "products"
        model.addAttribute("currentUser", user);
        model.addAttribute("products", allProducts);
        model.addAttribute("favoriteProduct", new FavoriteProducts());
        return "strona";
    }

    @PostMapping("/products/update-product")
    @ResponseBody // аннотация не подгружает страницу html, а просто передаёт параметры в файл js
    public ResponseEntity<?> updateProductParameters(@RequestParam Integer id, @RequestParam String description, @RequestParam String name, @RequestParam Integer price) {

        // Находим товар в базе
        Optional<Products> updateProduct = productsRepository.findById(id);

        if (updateProduct.isPresent()) {
            Products product = updateProduct.get();

            // Меняем описание
            product.setProductName(name);
            product.setProductDescription(description);
            product.setProductPrice(price);
            // Сохраняем обратно в базу
            productsRepository.save(product);

            // Ответ дя JS (HTTP 200 OK)
            return ResponseEntity.ok().build();
        }

        // Ошибка если нет товара
        return ResponseEntity.badRequest().build();
    }
    @PostMapping("/strona/favoriteProduct")
    @ResponseBody
    public ResponseEntity<?> favoriteProduct(@RequestParam Integer productId) {

        Optional<Products> optionalProduct = productsRepository.findById(productId);

        if (optionalProduct.isPresent()) {

            // передаём id продукта на сервис и если такого товара нет в избранном вернёт true
            // transmits the product id on the userService and, if that product is exist on the favorite, usersService.addToFavorite returns false else returns true
            boolean isAdded = usersService.addToFavorite(productId);

            if (isAdded) {
                return ResponseEntity.ok().build(); // 200 OK
            } else {
                boolean deleteProductFromFavorite =favoriteProductsServices.deleteFavoriteUserProduct(productId);
                if (deleteProductFromFavorite){
                    return ResponseEntity.status(HttpStatus.CONFLICT).build();
                }
                // 409 Conflict (Этот товар уже был добавлен в избранное)
            }
        }

        // 400 Bad Request (Товара с таким ID не существует)
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/products/favoriteProduct/exist")
    @ResponseBody
    public ResponseEntity<List<Integer>> favoriteProductExist() {

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
}
