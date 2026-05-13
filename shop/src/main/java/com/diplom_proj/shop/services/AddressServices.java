package com.diplom_proj.shop.services;

import com.diplom_proj.shop.dto.OrderRequestDTO;
import com.diplom_proj.shop.entity.Address;
import com.diplom_proj.shop.entity.Users;
import com.diplom_proj.shop.repository.AddressRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class AddressServices {
    private final UsersService usersService;
    private final AddressRepository addressRepository;

    public AddressServices(UsersService usersService, AddressRepository addressRepository) {
        this.usersService = usersService;
        this.addressRepository = addressRepository;
    }

    @Transactional
    public Address saveAddress(OrderRequestDTO request) {
        Users user = usersService.getCurrentUser();
        // Создание сущности Address
        Address newAddress = new Address();

        newAddress.setUser(user);
        newAddress.setName(request.getFirstName());
        newAddress.setSurname(request.getLastName());
        newAddress.setCountryAddress(request.getCountry());
        newAddress.setCityAddress(request.getCity());
        newAddress.setStreetAddress(request.getStreet());
        newAddress.setHouseNumberAddress(request.getBuildingNo());
        newAddress.setApartmentNumberAddress(request.getAptNo());
        newAddress.setCode(request.getZipCode());

        // Сохранение в базу
        return addressRepository.save(newAddress);
    }
}
