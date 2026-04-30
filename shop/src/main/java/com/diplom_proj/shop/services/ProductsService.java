package com.diplom_proj.shop.services;

import com.diplom_proj.shop.entity.Products;
import com.diplom_proj.shop.repository.ProductsRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductsService {
    public final ProductsRepository productsRepository;

    public ProductsService(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }

    public Products addNew(Products product) {
        product.setProductName(product.getProductName());
        product.setProductDescription(product.getProductDescription());
        product.setProductPrice(product.getProductPrice());
        product.setProductQuantity(product.getProductQuantity());
        product.setProductPhoto(product.getProductPhoto());

        productsRepository.save(product);
        return product;

    }

    public boolean updateProduct(int prodId, String prodName, String prodDesc, int prodPrice) {
        // Поиск товарв в базе
        Optional<Products> updateProduct = productsRepository.findById(prodId);
        if (updateProduct.isPresent()) {
            Products product = updateProduct.get();
            // Новое описание
            product.setProductName(prodName);
            product.setProductDescription(prodDesc);
            product.setProductPrice(prodPrice);
            // Сохранение обратно в базу
            productsRepository.save(product);

            // Ответ
            return true;
        }
        return false;
    }

    public Optional<Products> getByProductName(String name) {
        return productsRepository.findByProductName(name);
    }

    public List<Products> getAll() {
        return productsRepository.findAll(); // Get all products from database
    }

    public Optional<Products> getProduct(int id) {
        return productsRepository.findById(id);
    }
}
