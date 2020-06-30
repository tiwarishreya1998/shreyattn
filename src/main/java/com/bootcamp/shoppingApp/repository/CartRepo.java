package com.bootcamp.shoppingApp.repository;

import com.bootcamp.shoppingApp.Model.orderPack.Cart;
import com.bootcamp.shoppingApp.Model.user.Customer;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepo extends CrudRepository<Cart,Long> {

    @Query(value="select * from cart where product_variation_id=:varId AND customer_user_id=:custId ",nativeQuery = true)
    Optional<Cart> findByVariationIdAndCustomerID(@Param("varId") Long varId,@Param("custId") Long custId);

    List<Cart> findByCustomer(Customer customer);

    @Modifying
    @Query(value = "delete from cart where id=:id",nativeQuery = true)
    void deleteByCartId(@Param("id") Long id);
}

