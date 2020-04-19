package com.bootcamp.shoppingApp.repository;

import com.bootcamp.shoppingApp.Model.product.ProductVariation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProductVariationRepo extends CrudRepository<ProductVariation,Long> {

    @Query(value = "select max(price) from product_variation inner join product where product_variation.product_id=product.id AND product.category_id=:catId",nativeQuery = true)
    Optional<String> getMaxPrice(@Param("catId") Long catId);

    @Query(value = "select min(price) from product_variation inner join product where product_variation.product_id=product.id AND product.category_id=:catId",nativeQuery = true)
    Optional<String> getMinPrice(@Param("catId") Long catId);

}
