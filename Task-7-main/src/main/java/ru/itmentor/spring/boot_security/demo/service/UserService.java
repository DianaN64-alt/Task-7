package ru.itmentor.spring.boot_security.demo.service;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.itmentor.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService extends UserDetailsService {
    void save(User user);

    User findById(int id);

    List<User> findAll();

    void delete(int id);

    void update(User user, int id);

    @Override
    UserDetails loadUserByUsername(String email) throws UsernameNotFoundException;
}
