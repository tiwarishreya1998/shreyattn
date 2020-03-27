package com.bootcamp.shoppingApp.Repository;

import com.bootcamp.shoppingApp.Model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User,Long> {

    User findByEmail(String email);
    Optional<User> findById(Long id);
}
