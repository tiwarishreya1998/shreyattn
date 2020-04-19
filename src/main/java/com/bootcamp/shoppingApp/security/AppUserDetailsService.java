package com.bootcamp.shoppingApp.security;

import com.bootcamp.shoppingApp.utils.ValidEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AppUserDetailsService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDao userDao;


    private static final Logger LOGGER= LoggerFactory.getLogger(AppUserDetailsService.class);

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        boolean isValid=ValidEmail.checkEmailValid(email);
        if (!isValid){
            throw new RuntimeException("Email not valid");
        }
        String encryptedPassword=passwordEncoder.encode("pass");

        LOGGER.debug("Trying to authenticate user:{} ",email);

        LOGGER.debug("Encrypted Password :{} ",encryptedPassword);

        UserDetails userDetails=userDao.loadUserByEmail(email);
        return userDetails;
    }
}
