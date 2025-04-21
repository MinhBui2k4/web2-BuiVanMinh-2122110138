package com.example.BuiVanMinh.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;

@Entity
@Table(name = "carts_detail")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CartDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Min(value = 0, message = "Quantity must be non-negative")
    @Column(nullable = false)
    private long quantity;

    @Min(value = 0, message = "Price must be non-negative")
    @Column(nullable = false)
    private double price;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}