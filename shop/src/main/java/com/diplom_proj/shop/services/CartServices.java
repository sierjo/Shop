package com.diplom_proj.shop.services;

import com.diplom_proj.shop.entity.Carts;
import com.diplom_proj.shop.entity.Users;
import com.diplom_proj.shop.repository.CartRepository;
import com.diplom_proj.shop.repository.UsersRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CartServices {
    private final CartRepository cartRepository;
    private final UsersRepository usersRepository;

    public CartServices(CartRepository cartRepository, UsersRepository usersRepository) {
        this.cartRepository = cartRepository;
        this.usersRepository = usersRepository;
    }

    public boolean createCart() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Берём пользователя из авторизации
        // Getting a user through authentication
        Users user = usersRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
        if (cartRepository.existsCartsByUser_UserId(user.getUserId())) {
            return true;
        } else {
            Carts cart = new Carts();
            cart.setUser(user);
            cartRepository.save(cart);
            return true;
        }
    }
}
