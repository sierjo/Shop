package com.diplom_proj.shop.services;

import com.diplom_proj.shop.entity.Users;
import com.diplom_proj.shop.repository.UsersRepository;
import com.diplom_proj.shop.util.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailServices implements UserDetailsService {
    private final UsersRepository usersRepository;

    @Autowired
    public CustomUserDetailServices(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = usersRepository.findByEmail(username).orElseThrow(() ->
                new UsernameNotFoundException("Couldn't found user"));
        return new CustomUserDetails(user);
    }
}
