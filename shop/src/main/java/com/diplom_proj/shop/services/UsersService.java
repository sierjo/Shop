package com.diplom_proj.shop.services;

import com.diplom_proj.shop.entity.Roles;
import com.diplom_proj.shop.entity.Users;
import com.diplom_proj.shop.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class UsersService {
    private final UsersRepository usersRepository;
    private final UsersTypeService usersTypeService;
    //add PasswordEncoder
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsersService(UsersRepository usersRepository, UsersTypeService usersTypeService, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
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

    public Optional<Users> getUserByEmail(String email) {
        return usersRepository.findByEmail(email);
    }
}
