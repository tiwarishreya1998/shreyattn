package com.bootcamp.shoppingApp.Repository;

import com.bootcamp.shoppingApp.Model.Customer;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomerRepo extends CrudRepository<Customer,Long> {

    List<Customer> findAll(Pageable pageable);
}
