package com.bootcamp.shoppingApp.repository;

import com.bootcamp.shoppingApp.Model.product.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product,Long> {

    @Query(value = "select * from product where category_id=:id",nativeQuery = true)
    Product findByCategoryId(@Param("id") Long id);

    @Query(value="select * from product where id=:id category_id=:id1 AND brand=:brand AND name=:name",nativeQuery = true)
    Product findUniqueName(@Param("id")Long id,@Param("name")String name,@Param("brand")String brand,@Param("id1")Long id1);

    @Modifying
    @Query(value = "delete from product where id=:id", nativeQuery = true)
    void deleteByProductId(@Param("id") Long id);

    List<Product>findAll(Pageable pageable);
}
