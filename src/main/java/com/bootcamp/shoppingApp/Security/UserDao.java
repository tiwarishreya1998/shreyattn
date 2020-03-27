package com.bootcamp.shoppingApp.Security;

import com.bootcamp.shoppingApp.Model.User;
import com.bootcamp.shoppingApp.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDao {

    @Autowired
    private UserRepository userRepository;

    AppUser loadUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        System.out.println(user);
        if (email != null) {
            List<GrantAuthorityImpl> authorities = new ArrayList<>();
            user.getRoles().forEach(role -> {
                GrantAuthorityImpl grantAuthority = new GrantAuthorityImpl(role.getAuthority());
                authorities.add(grantAuthority);
            });
            return new AppUser(user.getFirstName(), user.getPassword(), authorities, user.isActive(), !user.isLocked()
                    , !user.isPasswordExpired());


        } else {
            throw new RuntimeException();


        }

    }
}














































