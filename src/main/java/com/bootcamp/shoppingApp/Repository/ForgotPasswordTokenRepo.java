package com.bootcamp.shoppingApp.Repository;

import com.bootcamp.shoppingApp.Model.ForgotPasswordToken;
import org.springframework.data.repository.CrudRepository;

public interface ForgotPasswordTokenRepo extends CrudRepository<com.bootcamp.shoppingApp.Model.ForgotPasswordToken,Long> {

    ForgotPasswordToken findByUserEmail(String email);
    void deleteByUserEmail(String email);
}
