package com.diplom_proj.shop.controller;

import com.diplom_proj.shop.entity.Roles;
import com.diplom_proj.shop.entity.Users;
import com.diplom_proj.shop.services.UsersService;
import com.diplom_proj.shop.services.UsersTypeService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class UsersController {

    private final UsersService usersService;
    private final UsersTypeService usersTypeService;

    public UsersController(UsersService usersService, UsersTypeService usersTypeService) {
        this.usersService = usersService;
        this.usersTypeService = usersTypeService;
    }

    @GetMapping("/register")
    public String register(Model model) {
//        List<Roles> roles = usersTypeService.getAll();
//        model.addAttribute("rolesList", roles);
        model.addAttribute("user", new Users());
//        return "register";
        return "register";
    }

    @PostMapping("/register/new")
    public String userRegistartion(@Valid Users users, Model model) {

        // Processing an error message related to email duplication ↓
        Optional<Users> opionalUser = usersService.getUserByEmail(users.getEmail());
        if (opionalUser.isPresent()) { // isPresent - Означает что opionalUser !=null
            model.addAttribute("error", "такой майл уже есть, try to login ore registration with other email.");

            model.addAttribute("user", new Users());

            return "register";
        }
        usersService.addNewKlien(users);
        return "home";
    }

}
