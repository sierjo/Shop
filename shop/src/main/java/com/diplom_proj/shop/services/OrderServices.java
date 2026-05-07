package com.diplom_proj.shop.services;

import com.diplom_proj.shop.dto.ProductDTO;
import com.diplom_proj.shop.entity.Address;
import com.diplom_proj.shop.entity.Users;
import com.diplom_proj.shop.repository.AddressRepository;
import com.diplom_proj.shop.repository.CartItemsRepository;
import com.diplom_proj.shop.repository.CartRepository;
import com.diplom_proj.shop.repository.UsersRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class OrderServices {
    private final UsersRepository usersRepository;
    private final CartRepository cartRepository;
    private final CartItemsRepository cartItemsRepository;
    private final UsersService usersService;
    private final AddressRepository addressRepository;

    public OrderServices(UsersRepository usersRepository, CartRepository cartRepository, CartItemsRepository cartItemsRepository, UsersService usersService, AddressRepository addressRepository) {
        this.usersRepository = usersRepository;
        this.cartRepository = cartRepository;
        this.cartItemsRepository = cartItemsRepository;
        this.usersService = usersService;
        this.addressRepository = addressRepository;
    }

    public ProductDTO dtoProdPrice() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Users user = usersRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
        ProductDTO productDTO = new ProductDTO();

        productDTO.setSumAllProductPrice(cartItemsRepository.getSumOfPricesByCartId(cartRepository.findCartsByUser_UserId(user.getUserId()).getCartsId()));
        System.out.println(productDTO);
        return productDTO;
    }

    public void saveUserAddress(String firstName, String lastName, String city, String street, String buildingNo, String aptNo, String zipCode) {

        // Получаем текущего пользователя через
        Users user = usersService.getCurrentUser();

        // Создание сущности Address
        Address newAddress = new Address();
        // Заполнение сущности
        newAddress.setUser(user);
        newAddress.setName(firstName);
        newAddress.setSurname(lastName);
        newAddress.setCityAddress(city);
        newAddress.setStreetAddress(street);
        newAddress.setHouseNumberAddress(buildingNo);
        newAddress.setApartmentNumberAddress(aptNo);
        newAddress.setCode(zipCode);
        newAddress.setCityAddress(city);

        // Сохраняем адрес в базу данных
        addressRepository.save(newAddress);
    }
}
