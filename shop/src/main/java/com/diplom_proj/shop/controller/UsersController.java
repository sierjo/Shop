package com.diplom_proj.shop.controller;

import com.diplom_proj.shop.services.UsersService;
import com.diplom_proj.shop.services.UsersTypeService;
import org.springframework.stereotype.Controller;

@Controller
public class UsersController {

    private final UsersService usersService;
    private final UsersTypeService usersTypeService;

    public UsersController(UsersService usersService, UsersTypeService usersTypeService) {
        this.usersService = usersService;
        this.usersTypeService = usersTypeService;
    }
}
