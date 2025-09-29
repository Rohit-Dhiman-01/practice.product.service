package com.ecommerce.product.service.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;

    @ManyToOne(cascade = CascadeType.PERSIST,  fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;
}
