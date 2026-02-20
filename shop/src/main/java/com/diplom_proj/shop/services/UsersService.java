package com.diplom_proj.shop.services;

import com.diplom_proj.shop.entity.Users;
import com.diplom_proj.shop.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsersService {
    private final UsersRepository usersRepository;
    //add PasswordEncoder
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsersService(UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Users addNew(Users user) {
        user.setActive(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        Users saveUser = usersRepository.save(user);
        usersRepository.save(user);
//        // Сохранение выбранной роли
//        int userRoleId = user.getRoleId().getUserTypeId();
//        if (userRoleId==1){
//
//        }
        return user;
    }

}
