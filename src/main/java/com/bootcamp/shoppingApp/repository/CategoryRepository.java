package com.bootcamp.shoppingApp.repository;

import com.bootcamp.shoppingApp.Model.categoryPack.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category,Long> {
    Category findByName(String name);

    @Query(value = "select * from category where parent_id=:id",nativeQuery = true)
    Optional<Category> findByParentId(@Param("id") Long id);
}
