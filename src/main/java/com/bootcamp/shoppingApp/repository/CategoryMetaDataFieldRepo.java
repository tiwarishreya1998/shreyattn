package com.bootcamp.shoppingApp.repository;

import com.bootcamp.shoppingApp.Model.categoryPack.CategoryMetaDataField;
import org.springframework.data.repository.CrudRepository;

public interface CategoryMetaDataFieldRepo extends CrudRepository<CategoryMetaDataField,Long> {
    CategoryMetaDataField findByName(String name);
}
