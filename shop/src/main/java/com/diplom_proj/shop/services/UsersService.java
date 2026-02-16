package com.diplom_proj.shop.services;

import com.diplom_proj.shop.entity.Users;
import com.diplom_proj.shop.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersService {
    private final UsersRepository usersRepository;

    @Autowired
    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

//    public Users addNew(Users user){
//        user.getRoleId();
//    }

    public void registeUser(Users user) {
        usersRepository.save(user);
        System.out.println("Пользователь успешно сохранён");
    }
}
