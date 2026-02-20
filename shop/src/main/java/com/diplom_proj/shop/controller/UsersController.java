package com.diplom_proj.shop.controller;

import com.diplom_proj.shop.entity.Roles;
import com.diplom_proj.shop.entity.Users;
import com.diplom_proj.shop.services.UsersService;
import com.diplom_proj.shop.services.UsersTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

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
        List<Roles>roles=usersTypeService.getAll();
        model.addAttribute("userTypeId",roles);
        model.addAttribute("user",new Users());
        return "register";
    }

}
