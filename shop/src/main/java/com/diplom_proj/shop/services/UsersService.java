package com.diplom_proj.shop.services;

import com.diplom_proj.shop.entity.FavoriteProducts;
import com.diplom_proj.shop.entity.Products;
import com.diplom_proj.shop.entity.Roles;
import com.diplom_proj.shop.entity.Users;
import com.diplom_proj.shop.dto.UsersDTO;
import com.diplom_proj.shop.repository.FavoriteProductRepository;
import com.diplom_proj.shop.repository.ProductsRepository;
import com.diplom_proj.shop.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsersService {
    private final UsersRepository usersRepository;
    private final ProductsRepository productsRepository;

    private final FavoriteProductRepository favoriteProductRepository;
    private final UsersTypeService usersTypeService;
    //add PasswordEncoder
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsersService(UsersRepository usersRepository, FavoriteProductRepository favoriteProductRepository, ProductsRepository productsRepository, UsersTypeService usersTypeService, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.productsRepository = productsRepository;
        this.favoriteProductRepository = favoriteProductRepository;
        this.usersTypeService = usersTypeService;
        this.passwordEncoder = passwordEncoder;
    }

    public Users addNewKlien(Users user) {
        user.setActive(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Roles klientRole = usersTypeService.getKlientRole().orElseThrow(() -> new RuntimeException("Роль Klient не найдена в базе!"));
        user.setRoleId(klientRole);
        user.setEmail(user.getEmail());
        user.setPhoneNumber(user.getPhoneNumber());
        usersRepository.save(user);
        return user;
    }

    public UsersDTO dtouser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<Users> user = usersRepository.findByEmail(authentication.getName());
        UsersDTO usersDTO = new UsersDTO();
        if (user.isPresent()) {
            usersDTO.setEmail(user.get().getEmail());
        }
        return usersDTO;
    }

    public boolean addToFavorite(int productId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Берём пользователя из авторизации
        // Getting a user through authentication
        Users user = usersRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));

        // Выбранный товар
        // That product User put a favorite
        Products product = productsRepository.findById(productId).orElseThrow();


        if (favoriteProductRepository.existsByUsers_UserIdAndProducts_ProductId(user.getUserId(),productId)) {
            return false; // If it finds the product in database favoriteProductRepository return true ( duplicate ) should return false 'don't push'
        }

        // Если всё чисто, сохраняем
        FavoriteProducts favorite = new FavoriteProducts();
        favorite.setUsers(user);
        favorite.setProducts(product);
        favoriteProductRepository.save(favorite);

        return true; // If it doesn't find a duplicate, favoriteProductRepository return false it will return true

    }

    public Optional<Users> getUserByEmail(String email) {
        return usersRepository.findByEmail(email);
    }
}
