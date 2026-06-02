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

    public List<Products> getAllOrFindProducts(String keyword) {
        if (keyword != null && !keyword.trim().isEmpty()) {

            // Очистка строкт от пробелов по краям и перевод в нижний регистр
            String cleanKeyword = keyword.trim().toLowerCase();

            // Разбивка на отдельные слова по пробелу: "маска для волос" -> "маска", "для", "волос"
            String[] words = cleanKeyword.split("\\s+");

            // Если слово всего одно просто вызов метода из репозитория
            if (words.length == 1) {
                return productsRepository.searchByKeyword(words[0]);
            }

            // Если слов несколько будут найдены товары где есть все введенные слова
            // Список товаров соответствующих первому слову
            List<Products> resultProducts = productsRepository.searchByKeyword(words[0]);

            // Фильтрация списка с оставлением товаров которые содержат все слова поисковика (как в названии так и в описании)
            for (int i = 1; i < words.length; i++) {
                String currentWord = words[i];
                resultProducts.removeIf(product ->
                        !(product.getProductName().toLowerCase().contains(currentWord) ||
                                product.getProductDescription().toLowerCase().contains(currentWord))
                );
            }

            return resultProducts;
        }

        // Если поиск пуст возврат всех товаров
        return productsRepository.findAll();
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


    public Optional<Products> getProduct(int id) {
        return productsRepository.findById(id);
    }
}
