package com.jnnieto.invetryx.product.catalog.service.repositories;

import com.jnnieto.invetryx.product.catalog.service.models.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product, String> {

    List<Product> findAll();

    Boolean existsByName(String name);

}
