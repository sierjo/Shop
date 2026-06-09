package com.diplom_proj.shop.repository;

import com.diplom_proj.shop.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Integer> {
    // Поиск всех адресов пользователя
    List<Address> findAllByUser_UserId(Integer userId);

    long deleteAddressByUser_UserIdAndAddressId(Integer UserId, Integer addressId);
}
