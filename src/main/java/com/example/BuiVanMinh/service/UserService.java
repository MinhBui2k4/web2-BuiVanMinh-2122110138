package com.example.BuiVanMinh.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.BuiVanMinh.domain.User;

@Service
public interface UserService {

    User createUser(User user);

    User getUserById(Long userId);

    List<User> getAllUsers();

    User updateUser(User user);

    void deleteUser(Long userId);
}
