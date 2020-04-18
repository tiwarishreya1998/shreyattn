package com.bootcamp.shoppingApp.repository;

import com.bootcamp.shoppingApp.Model.product.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product,Long> {
    @Query(value = "select * from product where category_id=:id",nativeQuery = true)
    List<Product> findByCategoryId(@Param("id") Long id);


}
