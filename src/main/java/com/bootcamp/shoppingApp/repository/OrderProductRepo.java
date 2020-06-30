package com.bootcamp.shoppingApp.repository;

import com.bootcamp.shoppingApp.Model.orderPack.OrderProduct;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderProductRepo extends CrudRepository<OrderProduct,Long> {

}
