package com.diplom_proj.shop.controller;

import com.diplom_proj.shop.entity.Products;
import com.diplom_proj.shop.services.ProductsService;
import com.diplom_proj.shop.util.FileUploadUtil;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Controller
public class ProductsController {
    public final ProductsService productsService;

    public ProductsController(ProductsService productsService) {
        this.productsService = productsService;
    }

    @GetMapping("/products")
    public String Product(Model model) {
        model.addAttribute("product", new Products());
        return "products";
    }

    @PostMapping("/products/add")
    public String addNewProduct(@Valid Products product, @RequestParam("filename") MultipartFile multipartFile,// Ловим файл из формы
                                Model model) throws IOException {
        Optional<Products> productName = productsService.getByProductName(product.getProductName());
        if (productName.isPresent()) {
            product.setProductName("");
            model.addAttribute("error", "Товар с таким именем уже есть");
            model.addAttribute("product", product); // то что пользователь успел ввести не удалится

            return "products";

        }
        String photoName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        if (!photoName.isEmpty()) {
            product.setProductPhoto(photoName);
        }
        Products savedProduct = productsService.addNew(product);
        if (!photoName.isEmpty()) {
            // Хорошая практика: создавать подпапку для каждого товара по его ID (photos/1/, photos/2/)
            // Это предотвратит баг, когда два разных товара имеют картинку "1.jpg" и перезаписывают друг друга.
//            String uploadDir = "photos/products/" + savedProduct.getProductId();
            String uploadDir = "photos/products/" + savedProduct.getProductId();

            // Вызываем нашу утилиту
            FileUploadUtil.saveFile(uploadDir, photoName, multipartFile);
//        productsService.addNew(product);
        }
        return "redirect:/products";
    }
}
