package com.bootcamp.shoppingApp.repository;

import com.bootcamp.shoppingApp.Model.user.Customer;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomerRepo extends CrudRepository<Customer,Long> {

    List<Customer> findAll(Pageable pageable);
    Customer findByEmail(String email);
}
