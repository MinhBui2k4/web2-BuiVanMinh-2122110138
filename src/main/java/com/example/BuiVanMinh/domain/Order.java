package com.example.BuiVanMinh.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotEmpty(message = "Name is required")
    private String name;

    @Column(nullable = false)
    @NotEmpty(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @Column(nullable = false)
    @NotEmpty(message = "Phone is required")
    private String phone;

    @Column(nullable = false)
    @NotEmpty(message = "Address is required")
    private String address;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}