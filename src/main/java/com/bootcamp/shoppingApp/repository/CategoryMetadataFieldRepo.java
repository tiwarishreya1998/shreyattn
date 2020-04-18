package com.bootcamp.shoppingApp.repository;

import com.bootcamp.shoppingApp.Model.categoryPack.CategoryMetadataField;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CategoryMetadataFieldRepo extends CrudRepository<CategoryMetadataField,Long> {
    CategoryMetadataField findByName(String name);
    List<CategoryMetadataField> findAll(Pageable pageable);
}
