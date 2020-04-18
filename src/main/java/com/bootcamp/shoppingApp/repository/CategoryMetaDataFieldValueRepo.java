package com.bootcamp.shoppingApp.repository;

import com.bootcamp.shoppingApp.Model.categoryPack.CategoryMetaDataFieldValues;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CategoryMetaDataFieldValueRepo extends CrudRepository<CategoryMetaDataFieldValues,Long> {
    @Query(value = "select * from category_metadata_field_values where category_id=:cId AND category_metadata_field_id=:mId",nativeQuery = true)
    Optional<CategoryMetaDataFieldValues> findByMetadataId(@Param("cId") Long cId, @Param("mId") Long mId);

    @Query(value = "select category_metadata_field.name,category_metadata_field_values.value from category_metadata_field_values inner join category_metadata_field on category_metadata_field_values.category_metadata_field_id = category_metadata_field.id AND category_metadata_field_values.category_id=:id",nativeQuery = true)
    List<Object[]> findCategoryMetadataFieldValuesById(@Param("id") Long id);
}
