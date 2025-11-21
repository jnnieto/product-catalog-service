package com.jnnieto.invetryx.product.catalog.service.repositories;

import com.jnnieto.invetryx.product.catalog.service.models.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends CrudRepository<Category, String> {
    
    List<Category> findAll();

    Boolean existsByName(String name);

}
