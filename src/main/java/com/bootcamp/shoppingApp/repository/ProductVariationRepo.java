package com.bootcamp.shoppingApp.repository;

import com.bootcamp.shoppingApp.Model.product.ProductVariation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductVariationRepo extends CrudRepository<ProductVariation,Long> {
    @Query(value = "select * from product_variation where product_id=:id",nativeQuery = true)
    List< ProductVariation> findByProductId(@Param("id")Long id, Pageable pageable);
    @Modifying
    @Query(value = "delete from product_variation where product_id=:id",nativeQuery = true)
    void deleteByProductId(@Param("id") Long id);

}
