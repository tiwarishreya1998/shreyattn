package com.bootcamp.shoppingApp.security;

import com.bootcamp.shoppingApp.Model.user.Admin;
import com.bootcamp.shoppingApp.Model.user.Role;
import com.bootcamp.shoppingApp.repository.CategoryRepository;
import com.bootcamp.shoppingApp.repository.ProductRepository;
import com.bootcamp.shoppingApp.repository.ProductVariationRepo;
import com.bootcamp.shoppingApp.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Component
public class Bootstrap implements ApplicationRunner {

    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductVariationRepo productVariationRepo;
    private static final Logger LOGGER= LoggerFactory.getLogger(Bootstrap.class);

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (userRepository.count()<1){
            PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();


            Admin shreya=new Admin();
            shreya.setFirstName("shreya");
            shreya.setLastName("Tiwari");
            shreya.setEmail("shreya@admin.com");
            shreya.setCreatedBy("shreya");
            shreya.setDateCreated(new Date());
            shreya.setLastUpdated(new Date());
            shreya.setUpdatedBy("shreya");
            shreya.setActive(true);
            shreya.setDeleted(false);
            shreya.setPassword(passwordEncoder.encode("pass"));

            Role role=new Role();
            role.setAuthority("ROLE_ADMIN");
            Role role1=new Role();
            role1.setAuthority("ROLE_CUSTOMER");

            Set<Role> roleSet=new HashSet<>();
            roleSet.add(role);
            roleSet.add(role1);

            shreya.setRoles(roleSet);

            userRepository.save(shreya);
            LOGGER.debug("Total user saved");

        }

    }
}
