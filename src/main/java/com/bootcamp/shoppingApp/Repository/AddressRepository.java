package com.bootcamp.shoppingApp.Repository;

import com.bootcamp.shoppingApp.Model.Address;
import org.springframework.data.repository.CrudRepository;

public interface AddressRepository extends CrudRepository<Address,Long> {
}
