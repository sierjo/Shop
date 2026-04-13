package com.diplom_proj.shop.services;

import com.diplom_proj.shop.entity.Users;
import com.diplom_proj.shop.repository.FavoriteProductRepository;
import com.diplom_proj.shop.repository.UsersRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class FavoriteProductsServices {
    private final FavoriteProductRepository favoriteProductRepository;
    private final UsersRepository usersRepository;

    public FavoriteProductsServices(FavoriteProductRepository favoriteProductRepository, UsersRepository usersRepository) {
        this.favoriteProductRepository = favoriteProductRepository;
        this.usersRepository = usersRepository;
    }

    public boolean deleteFavoriteUserProduct(int productId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Берём пользователя из авторизации
        // Getting a user through authentication
        Users user = usersRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
        favoriteProductRepository.deleteByUsers_UserIdAndProducts_ProductId(user.getUserId(),productId);
        return true;
    }
}
