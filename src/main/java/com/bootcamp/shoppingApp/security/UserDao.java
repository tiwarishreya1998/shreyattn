package com.bootcamp.shoppingApp.security;

import com.bootcamp.shoppingApp.Model.user.User;
import com.bootcamp.shoppingApp.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDao {

    @Autowired
    private UserRepository userRepository;

    private static final Logger LOGGER= LoggerFactory.getLogger(UserDao.class);

    AppUser loadUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        LOGGER.debug("-------------USER DAO------------------{}",user);
        if (email != null) {
            List<GrantAuthorityImpl> authorities = new ArrayList<>();
            user.getRoles().forEach(role -> {
                GrantAuthorityImpl grantAuthority = new GrantAuthorityImpl(role.getAuthority());
                authorities.add(grantAuthority);
            });
            return new AppUser(user.getFirstName(), user.getPassword(), authorities, user.isActive(), !user.isLocked()
                    , !user.isPasswordExpired());


        } else {
            throw new RuntimeException("User not found");

        }

    }
}














































