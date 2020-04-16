package com.bootcamp.shoppingApp.repository;

import com.bootcamp.shoppingApp.Model.categoryPack.CategoryMetaDataFieldValues;
import org.springframework.data.repository.CrudRepository;

public interface CategoryMetaDataFieldValueRepo extends CrudRepository<CategoryMetaDataFieldValues,Long> {
}
