package com.bootcamp.shoppingApp.repository;

import com.bootcamp.shoppingApp.Model.product.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends CrudRepository<Product,Long> {
    @Query(value = "select * from product where category_id=:id",nativeQuery = true)
    List<Product> findByCategoryId(@Param("id") Long id);

    @Query(value = "select brand from product where category_id=:id",nativeQuery = true)
    List<String> getBrandsOfCategory(@Param("id") Long id);

    @Query(value = "select * from product where category_id=:id AND is_deleted=false AND is_active=true",nativeQuery = true)
    List<Product> getAllProductsOfCategory(@Param("id") Long id,Pageable pageable);

    @Query(value = "select * from product where brand=:brand AND name=:name AND seller_user_id=:sellerId AND category_id=:categoryId",nativeQuery = true)
    Optional<Product> checkUniqueProductName(@Param("brand") String brand, @Param("name") String name, @Param("sellerId") Long sellerId, @Param("categoryId") Long categoryId);

    @Query(value = "select * from product where seller_user_id=:id AND is_deleted=false",nativeQuery = true)
    List<Product> productsOfSeller(@Param("id") Long id, Pageable pageable);

    @Query(value = "select * from product where is_deleted=false AND is_active=true",nativeQuery = true)
    List<Product> getAllProductsNonDeletedActive(Pageable pageable);

    @Query(value = "select is_deleted from product where id=:id",nativeQuery = true)
    Boolean getDeletedStatus(@Param("id") Long id);
}
