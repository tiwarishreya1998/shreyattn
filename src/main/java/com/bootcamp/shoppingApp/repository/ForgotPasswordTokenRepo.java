package com.bootcamp.shoppingApp.repository;

import com.bootcamp.shoppingApp.Model.user.ForgotPasswordToken;
import org.springframework.data.repository.CrudRepository;

public interface ForgotPasswordTokenRepo extends CrudRepository<ForgotPasswordToken,Long> {

    ForgotPasswordToken findByUserEmail(String email);
    void deleteByUserEmail(String email);
}
