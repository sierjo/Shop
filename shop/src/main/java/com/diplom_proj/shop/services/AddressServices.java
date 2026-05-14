package com.diplom_proj.shop.services;

import com.diplom_proj.shop.dto.OrderRequestDTO;
import com.diplom_proj.shop.entity.Address;
import com.diplom_proj.shop.entity.Users;
import com.diplom_proj.shop.repository.AddressRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

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
        // Если пользователь выбрал существующий адрес из списка адресов
        if (request.getSelectedAddressId() != null) {
            Address existing = addressRepository.findById(request.getSelectedAddressId())
                    .orElseThrow(() -> new RuntimeException("Адрес не найден"));

            // Если пользователь выбрал перезапись адреса (Нажал "Отмена" в окне)
            if (request.isOverwriteExisting()) {
                existing.setName(request.getFirstName());
                existing.setSurname(request.getLastName());
                existing.setCountryAddress(request.getCountry());
                existing.setCityAddress(request.getCity());
                existing.setStreetAddress(request.getStreet());
                existing.setHouseNumberAddress(request.getBuildingNo());
                existing.setApartmentNumberAddress(request.getAptNo());
                existing.setCode(request.getZipCode());

                return addressRepository.save(existing); // Сохраняем обновления
            }
            // возвращает адрес
            return existing;
        }
        // Если ID пустой (Создст новый адрес)
        if (request.isSaveNewAddress()) {
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

            return addressRepository.save(newAddress);
        }

        // Если не ставиль галочку Сохранить
        return null;
    }

    public List<Address> getUserAddressList() {
        Users user = usersService.getCurrentUser();
        return addressRepository.findAllByUser_UserId(user.getUserId());
    }
}
