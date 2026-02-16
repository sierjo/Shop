package com.diplom_proj.shop.services;

import com.diplom_proj.shop.entity.Roles;
import com.diplom_proj.shop.repository.UsersTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersTypeService {
    private final UsersTypeRepository usersTypeRepository;

    @Autowired
    public UsersTypeService(UsersTypeRepository usersTypeRepository) {
        this.usersTypeRepository = usersTypeRepository;
    }

    // Метод: найти роль или создать, если нет (чтобы не дублировать)
    public Roles findOrCreateRole(int roleName) {
        // Здесь нужна бы логика поиска по имени, но пока сделаем простую вставку
        // или вернем первую попавшуюся для примера.
        // В идеале в репозиторий добавить: Optional<Roles> findByUserTypeName(String name);

        Roles role = new Roles();
        role.setUserTypeId(roleName);
        return usersTypeRepository.save(role);
    }
}