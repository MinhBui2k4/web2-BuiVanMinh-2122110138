package com.example.BuiVanMinh.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.example.BuiVanMinh.domain.User;
import com.example.BuiVanMinh.Jwt.JwtUtil;
import com.example.BuiVanMinh.dto.Login;
import com.example.BuiVanMinh.service.UserService;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*") // Cho phép frontend gọi API
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    // Lưu tạm token đăng ký để xác minh khi login
    private final Map<String, String> registrationTokens = new HashMap<>();

    @Autowired
    public UserController(UserService userService, JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerUser(@RequestBody User user) {
        if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("USER");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword())); // Mã hóa mật khẩu
        User savedUser = userService.createUser(user);
        String token = jwtUtil.generateToken(savedUser.getEmail());

        Map<String, String> response = new HashMap<>();
        response.put("message", "Đăng ký thành công");
        response.put("email", savedUser.getEmail());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody Login loginRequest) {
        Optional<User> userOptional = userService.getUserByEmail(loginRequest.getEmail());

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
                String newToken = jwtUtil.generateToken(user.getEmail());

                Map<String, String> response = new HashMap<>();
                response.put("message", "Đăng nhập thành công");
                response.put("token", newToken);
                response.put("email", user.getEmail());
                response.put("role", user.getRole());
                response.put("userId", String.valueOf(user.getId()));

                return ResponseEntity.ok(response);
            }
        }

        return ResponseEntity.status(401).body(Map.of("message", "Email hoặc mật khẩu không đúng"));
    }

    // GET tất cả người dùng
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers(); // Lấy tất cả người dùng từ service
        return ResponseEntity.ok(users); // Trả về danh sách người dùng
    }

    // GET thông tin người dùng theo ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id); // Tìm người dùng theo ID
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build()); // Trả về 404 nếu không tìm thấy
    }

    // PUT cập nhật thông tin người dùng
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        return userService.getUserById(id).map(existingUser -> {
            existingUser.setName(userDetails.getName());
            existingUser.setEmail(userDetails.getEmail());
            existingUser.setPhone(userDetails.getPhone());
            User updatedUser = userService.updateUser(id, existingUser);
            return ResponseEntity.ok(updatedUser);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // DELETE xóa người dùng
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (!userService.getUserById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
