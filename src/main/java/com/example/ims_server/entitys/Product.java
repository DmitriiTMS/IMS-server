package com.example.ims_server.entitys;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "products")
@Data
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Название продукта обязательно")
    private String name;

    @Column(unique = true)
    @NotBlank(message = "SKU обязательно")
    private String sku;

    @Positive(message = "Цена продукта должна быть положительным значением")
    private BigDecimal price;

    @Min(value = 0, message = "Количество на складе не может быть отрицательным")
    private Integer stockQuantity;

    private String description;
    private LocalDateTime expiryDate;
    private String imageUrl;

    private final LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany(mappedBy = "product")
    private List<Transaction> transactions;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

}