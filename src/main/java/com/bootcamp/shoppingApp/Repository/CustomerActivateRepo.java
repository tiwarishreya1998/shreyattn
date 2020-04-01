package com.bootcamp.shoppingApp.Repository;

import com.bootcamp.shoppingApp.Model.CustomerActivate;
import org.springframework.data.repository.CrudRepository;

public interface CustomerActivateRepo extends CrudRepository<CustomerActivate,Long>
{
    CustomerActivate findByUserEmail(String email);
    void deleteByEmail(String email);
}
