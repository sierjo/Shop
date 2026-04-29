package com.diplom_proj.shop.services;

import com.diplom_proj.shop.dto.ProductDTO;
import com.diplom_proj.shop.dto.UsersDTO;
import com.diplom_proj.shop.entity.CartItems;
import com.diplom_proj.shop.entity.Products;
import com.diplom_proj.shop.entity.Users;
import com.diplom_proj.shop.repository.CartItemsRepository;
import com.diplom_proj.shop.repository.CartRepository;
import com.diplom_proj.shop.repository.ProductsRepository;
import com.diplom_proj.shop.repository.UsersRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CartItemsServices {

    private final CartRepository cartRepository;
    private final ProductsRepository productsRepository;
    private final UsersRepository usersRepository;
    private final CartItemsRepository cartItemsRepository;

    public CartItemsServices(CartRepository cartRepository, ProductsRepository productsRepository, UsersRepository usersRepository, CartItemsRepository cartItemsRepository) {
        this.cartRepository = cartRepository;
        this.productsRepository = productsRepository;
        this.usersRepository = usersRepository;
        this.cartItemsRepository = cartItemsRepository;
    }

    public boolean addToCartItem(int productId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Берём пользователя из авторизации
        // Getting a user through authentication
        Users user = usersRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
        // Выбранный товар
        // That product User put a favorite
        Products product = productsRepository.findById(productId).orElseThrow();

        // Если всё чисто, сохраняем
        CartItems item = new CartItems();
        item.setCarts(cartRepository.getCartsByUser_UserId(user.getUserId()));
        item.setProduct(product);
        item.setPriceInCartItem(product.getProductPrice());
        item.setPriceInCartItem(1);
        cartItemsRepository.save(item);

        return true; // If it doesn't find a duplicate, favoriteProductRepository return false it will return true

    }

    public List<Integer> getAllUsersProductIdInCart() {
        // Текущий пользователя
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Достаем пользователя из базы данных
        Users user = usersRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));

        // Если пользователь не авторизован, прерываемся
        if (authentication.getName().equals("ROLE_ANONYMOUS")) {
            return Collections.emptyList();
        }

        if (user.getRoleId().getUserTypeName().equals("Klient") && cartRepository.existsCartsByUser_UserId(user.getUserId())) {
            // Если это Клиент - обращаемся к репозиторию и возвращаем список ID его товаров
            System.out.println("List PRODUCTS");
            System.out.println(cartItemsRepository.getAllProductsIdsInCartByCartId(cartRepository.findCartsByUser_UserId(user.getUserId()).getCartsId()));
            return cartItemsRepository.getAllProductsIdsInCartByCartId(cartRepository.findCartsByUser_UserId(user.getUserId()).getCartsId());
        }
        // Во всех остальных случаях (если это не Клиент и т.д.) возвращаем пустой список
        return Collections.emptyList();
    }

    public List<Products> getAllUsersProductInCart() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Users user = usersRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));

        if (cartRepository.existsCartsByUser_UserId(user.getUserId())) {
            return cartItemsRepository.getAllProductsInCartByCartId(cartRepository.getCartsByUser_UserId(user.getUserId()).getCartsId());
        } else {
            return Collections.emptyList();
        }
    }


    public boolean existProductInCartItem(int productId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Достаем пользователя из базы данных
        Users user = usersRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
        if (cartItemsRepository.existsByCarts_CartsIdAndProduct_ProductId(cartRepository.findCartsByUser_UserId(user.getUserId()).getCartsId(), productId)) {
            return true;
        }
        return false;
    }

    public boolean deleteProductFromCartItem(int productId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Достаем пользователя из базы данных
        Users user = usersRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));

        cartItemsRepository.deleteByCarts_CartsIdAndProduct_ProductId(cartRepository.findCartsByUser_UserId(user.getUserId()).getCartsId(), productId);
        return true;
    }

    public boolean TestOnEmptyProducts() {
        return !cartItemsRepository.findAll().isEmpty();
    }

    public ProductDTO dtoProdPrice() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Достаем пользователя из базы данных
        Users user = usersRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
        ProductDTO productDTO = new ProductDTO();

        productDTO.setSumAllProductPrice(cartItemsRepository.getSumOfPricesByCartId(cartRepository.findCartsByUser_UserId(user.getUserId()).getCartsId()));
        System.out.println(productDTO);
        return productDTO;
    }
}
