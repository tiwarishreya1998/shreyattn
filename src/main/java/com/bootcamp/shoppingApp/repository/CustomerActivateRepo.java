package com.bootcamp.shoppingApp.repository;

import com.bootcamp.shoppingApp.Model.user.CustomerActivate;
import org.springframework.data.repository.CrudRepository;

public interface CustomerActivateRepo extends CrudRepository<CustomerActivate,Long>
{
    CustomerActivate findByUserEmail(String email);
    CustomerActivate findByToken(String token);
    void deleteByUserEmail(String email);
}
