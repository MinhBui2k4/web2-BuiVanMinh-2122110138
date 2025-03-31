package com.example.BuiVanMinh.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import com.example.BuiVanMinh.domain.User;
import com.example.BuiVanMinh.repository.UserRepository;
import com.example.BuiVanMinh.service.UserService;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User updateUser(User user) {
        User existingUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getFullName() != null)
            existingUser.setFullName(user.getFullName());
        if (user.getEmail() != null)
            existingUser.setEmail(user.getEmail());
        if (user.getPassword() != null)
            existingUser.setPassword(user.getPassword());
        if (user.getAddress() != null)
            existingUser.setAddress(user.getAddress());
        if (user.getPhone() != null)
            existingUser.setPhone(user.getPhone());
        if (user.getAvatar() != null)
            existingUser.setAvatar(user.getAvatar());
        if (user.getRole() != null)
            existingUser.setRole(user.getRole());

        return userRepository.save(existingUser);
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

}
