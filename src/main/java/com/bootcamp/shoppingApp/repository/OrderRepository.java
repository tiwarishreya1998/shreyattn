package com.bootcamp.shoppingApp.repository;

import com.bootcamp.shoppingApp.Model.orderPack.OrderT;
import com.bootcamp.shoppingApp.Model.user.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository  extends CrudRepository<OrderT,Long> {


    List<OrderT> findByCustomer(Customer customer);
}
