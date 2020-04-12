package com.bootcamp.shoppingApp.repository;

import com.bootcamp.shoppingApp.Model.user.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User,Long> {

    User findByEmail(String email);
    Optional<User> findById(Long id);
}
