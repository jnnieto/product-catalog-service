package com.jnnieto.invetryx.product.catalog.service.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(length = 50)
    private String name;

    @OneToMany(mappedBy = "category")
    private List<Product> products;

}
