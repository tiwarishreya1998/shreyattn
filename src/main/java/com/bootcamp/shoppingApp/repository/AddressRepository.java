package com.bootcamp.shoppingApp.repository;


import com.bootcamp.shoppingApp.Model.user.Address;
import org.springframework.data.repository.CrudRepository;

public interface AddressRepository extends CrudRepository<Address,Long> {
}
