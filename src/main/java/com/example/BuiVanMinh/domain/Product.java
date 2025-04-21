package com.example.BuiVanMinh.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    @NotEmpty(message = "Product name is required")
    private String name;

    @Column(length = 500)
    private String image;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    @Min(value = 0, message = "Price must be non-negative")
    private float price;

    @Column(name = "quantity", nullable = false)
    @Min(value = 0, message = "Quantity must be non-negative")
    private Integer quantity;
}