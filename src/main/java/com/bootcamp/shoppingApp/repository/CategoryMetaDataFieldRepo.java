package com.bootcamp.shoppingApp.repository;

import com.bootcamp.shoppingApp.Model.categoryPack.CategoryMetaDataField;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CategoryMetaDataFieldRepo extends CrudRepository<CategoryMetaDataField,Long> {
    CategoryMetaDataField findByName(String name);
    List<CategoryMetaDataField> findAll(Pageable pageable);
}
