package com.bootcamp.shoppingApp.repository;

import com.bootcamp.shoppingApp.Model.user.UserLoginFailCounter;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserLoginFailCounterRepository extends CrudRepository<UserLoginFailCounter,Long> {
    Optional<UserLoginFailCounter> findByEmail(String email);
}
