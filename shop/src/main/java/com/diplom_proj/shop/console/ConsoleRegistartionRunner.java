package com.diplom_proj.shop.console;

import com.diplom_proj.shop.entity.Roles;
import com.diplom_proj.shop.entity.Users;
import com.diplom_proj.shop.repository.UsersRepository;
import com.diplom_proj.shop.repository.UsersTypeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Scanner;

@Component
public class ConsoleRegistartionRunner implements CommandLineRunner {
    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private UsersTypeRepository rolesRepository;

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);

//        System.out.println("===== РЕЖИМ ДОБАВЛЕНИЯ ПОЛЬЗОВАТЕЛЯ =====");
//
//        // 1. Ввод Email
//        System.out.print("Введите Email: ");
//        String email = scanner.nextLine();
//
//        // 2. Ввод Пароля
//        System.out.print("Введите Пароль: ");
//        String password = scanner.nextLine();
//
//        // 3. Ввод Телефона
//        System.out.print("Введите номер телефона: ");
//        String phone = scanner.nextLine();
//
//        // 4. Выбор Роли
//        System.out.println("Выберите ID роли:");
//        // Выводим список доступных ролей из базы, чтобы пользователь знал, что жать
//        rolesRepository.findAll().forEach(role ->
//                System.out.println(role.getUserTypeId() + " - " + role.getUserTypeName())
//        );
//
//        System.out.print("Ваш выбор (число): ");
//        int selectedRoleId = Integer.parseInt(scanner.nextLine());
//
//        // 5. Поиск роли в базе данных
//        // Важно: мы не создаем new Roles(), мы берем существующую из БД!
//        Roles role = rolesRepository.findById(selectedRoleId)
//                .orElseThrow(() -> new RuntimeException("Роль с таким ID не найдена!"));
//
//        // 6. Создание и сохранение пользователя
//        Users newUser = new Users();
//        newUser.setEmail(email);
//        newUser.setPassword(password);
//        newUser.setPhoneNumber(phone);
//        newUser.setActive(true); // По умолчанию активен
//        newUser.setRoleId(role); // Присваиваем найденный объект роли
//
//        // Дата регистрации проставится сама благодаря @CreationTimestamp
//
//        usersRepository.save(newUser);
//
//        System.out.println("Пользователь успешно сохранен с ID: " + newUser.getUserId());
//        System.out.println("Роль пользователя: " + newUser.getRoleId().getUserTypeName());


        System.out.println("===== РЕЖИМ Удаления =====");

        // 1. Ввод Email
        System.out.print("Выберите пользователя для удаления: ");
        usersRepository.findAll().forEach(users ->
                System.out.println(users.getUserId() + " " + users.getEmail() + " " + users.getRoleId()));

        System.out.print("Ваш выбор (число): ");
        int selectedForDeleted = Integer.parseInt(scanner.nextLine());
        Users findUserForDel = usersRepository.findById(selectedForDeleted)
                .orElseThrow(() -> new RuntimeException("Пользователь с таким ID не найден!"));

        // 3. Теперь println сработает, так как объект настоящий
        System.out.println("Выбран: " + findUserForDel.getEmail());

        // 4. Удаляем
        usersRepository.delete(findUserForDel);
        System.out.println("Пользователь удалён!");
        scanner.close();
    }


}