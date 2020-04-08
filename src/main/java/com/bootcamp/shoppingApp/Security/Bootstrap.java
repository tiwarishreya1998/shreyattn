package com.bootcamp.shoppingApp.Security;

import com.bootcamp.shoppingApp.Model.Admin;
import com.bootcamp.shoppingApp.Model.Role;

import com.bootcamp.shoppingApp.Repository.UserRepository;
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
            System.out.println("Total users saved");

        }
    }
}
