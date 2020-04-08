package com.bootcamp.shoppingApp.Security;

import com.bootcamp.shoppingApp.Model.User;
import com.bootcamp.shoppingApp.Repository.UserRepository;
import com.bootcamp.shoppingApp.Utils.ValidEmail;
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



    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        boolean isValid=ValidEmail.checkEmailValid(email);
        if (!isValid){
            throw new RuntimeException("Email not valid");//response will go to postman that email is not valid.
        }
        String encryptedPassword=passwordEncoder.encode("pass");
        System.out.println("Trying to authenticate user: "+email);

        System.out.println("Encrypted password :: "+encryptedPassword);

        UserDetails userDetails=userDao.loadUserByEmail(email);
        return userDetails;
    }
}
