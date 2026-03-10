package com.diplom_proj.shop.services;

import com.diplom_proj.shop.entity.Products;
import com.diplom_proj.shop.repository.ProductsRepository;
import org.springframework.stereotype.Service;

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

    public Optional<Products> getByProductName(String name) {
        return productsRepository.findByProductName(name);
    }
}
