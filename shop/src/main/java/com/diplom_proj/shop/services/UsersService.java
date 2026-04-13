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

import java.util.Collections;
import java.util.List;
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


        if (favoriteProductRepository.existsByUsers_UserIdAndProducts_ProductId(user.getUserId(), productId)) {
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

    public List<Products> getAllUsersProduct() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Users user = usersRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
        System.out.println("PRINT FAVORITE PRODUCT--------------------------------------------------------------------------------------------------------------------");
//        System.out.println("FAVORITE PROD ------->    "+favoriteProductRepository.findAllByUsers_UserId(user.getUserId()));
        System.out.println("END__PRINT FAVORITE PRODUCT--------------------------------------------------------------------------------------------------------------------");
//        return favoriteProductRepository.findAllByUsers_UserId(user.getUserId());
        List<Products> favorites = favoriteProductRepository.findAllByUsers_UserId(user.getUserId());
//        List<Products> favoriteProducts = productsRepository.findAllById(favorites);

        System.out.println(favorites);

        return favorites;
    }

    public List<Integer> getAllFUsersProduct() {
        // Текущий пользователя
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Достаем пользователя из базы данных
        Users user = usersRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));

        // Если пользователь не авторизован, прерываемся
        if (authentication.getName().equals("ROLE_ANONYMOUS")) {
            return Collections.emptyList();
        }

        if (user.getRoleId().getUserTypeName().equals("Klient")) {
            // Если это Клиент - обращаемся к репозиторию и возвращаем список ID его товаров
            return favoriteProductRepository.getAllProductsFUdByUsers(user.getUserId());
        }
        // Во всех остальных случаях (если это не Клиент и т.д.) возвращаем пустой список
        return Collections.emptyList();
    }
}
