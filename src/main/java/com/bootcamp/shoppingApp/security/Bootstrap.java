package com.bootcamp.shoppingApp.security;

import com.bootcamp.shoppingApp.Model.categoryPack.Category;
import com.bootcamp.shoppingApp.Model.product.Product;
import com.bootcamp.shoppingApp.Model.product.ProductVariation;
import com.bootcamp.shoppingApp.Model.user.Admin;
import com.bootcamp.shoppingApp.Model.user.Role;
import com.bootcamp.shoppingApp.repository.CategoryRepository;
import com.bootcamp.shoppingApp.repository.ProductRepository;
import com.bootcamp.shoppingApp.repository.ProductVariationRepo;
import com.bootcamp.shoppingApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;

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
        if(productRepository.count()<1)
        {
            Product product=new Product();
            Category category=new Category();;
            category.setName("FootWear");
            category.setParentId(category);
            categoryRepository.save(category);
            product.setCategory(category);
            product.setActive(true);
            product.setDescription("Footwear is very good");
            product.setBrand("Adidas");
            productRepository.save(product);

            if (productVariationRepo.count()<1){
                ProductVariation productVariation=new ProductVariation();
                productVariation.setPrice(100L);
                productVariation.setQuantityAvailable(90L);
                productVariation.setProduct(product);
                Map<String,Object> map=new HashMap<>();
                map.put("Colour",new String("red"));
                productVariation.setMetadata(map);
                productVariationRepo.save(productVariation);
            }
            System.out.println("total product save  "+productRepository.count());
        }
    }
}
