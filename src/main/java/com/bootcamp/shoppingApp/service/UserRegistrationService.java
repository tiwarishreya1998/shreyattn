package com.bootcamp.shoppingApp.service;

import com.bootcamp.shoppingApp.Model.user.*;
import com.bootcamp.shoppingApp.repository.CustomerActivateRepo;
import com.bootcamp.shoppingApp.repository.SellerRepository;
import com.bootcamp.shoppingApp.repository.UserRepository;
import com.bootcamp.shoppingApp.utils.SendEmail;
import com.bootcamp.shoppingApp.utils.ValidEmail;
import com.bootcamp.shoppingApp.utils.ValidGst;
import com.bootcamp.shoppingApp.utils.ValidPassword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserRegistrationService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CustomerActivateRepo customerActivateRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private SellerRepository sellerRepository;
    @Autowired
    private SendEmail sendEmail;

    public String registerCustomer(Customer customer){
        boolean isEmailValid=ValidEmail.checkEmailValid(customer.getEmail());
        if (!isEmailValid){
            return "Email is not valid";
        }
        User user=userRepository.findByEmail(customer.getEmail());
        try{
            if (user.getEmail().equals(customer.getEmail())){
                return "Email already exists";   }
        }

        catch(NullPointerException ex){
            ex.printStackTrace();
        }

        boolean isPasswordValid=ValidPassword.isValidPassword(customer.getPassword());
        if(!isPasswordValid){
            return "Password is not appropriate";
        }
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));


        if(customer.getContact().length()!=10){
            return "Invalid contact";
        }

        Role role=new Role();
        role.setAuthority("ROLE_CUSTOMER");
        Set<Role> roleSet=new HashSet<>();
        roleSet.add(role);
        customer.setRoles(roleSet);
        customer.setCreatedBy(customer.getFirstName());
        customer.setDateCreated(new Date());
        customer.setLocked(false);
        customer.setPasswordExpired(false);

        userRepository.save(customer);

        String token= UUID.randomUUID().toString();
        CustomerActivate customerActivate=new CustomerActivate();
        customerActivate.setToken(token);
        customerActivate.setUserEmail(customer.getEmail());
        customerActivate.setExpiryDate(new Date());

        customerActivateRepo.save(customerActivate);

        sendEmail.sendEmail("Account Activation Token",token,customer.getEmail());
        return "Success";

    }

    public String registerSeller(Seller seller)
    {
        boolean isValidGst=ValidGst.checkGstValid(seller.getGst());
            if(!isValidGst){
                return "Gst is not valid";
            }

        boolean isValidEmail=ValidEmail.checkEmailValid(seller.getEmail());
            if(!isValidEmail){
                return "Email is not valid";
            }


            User user=userRepository.findByEmail(seller.getEmail());
            try{
            if (user.getEmail().equals(seller.getEmail())){
                return "Email already exists";
            }
            }
            catch (NullPointerException ex){
                ex.printStackTrace();
            }

            Seller seller1=sellerRepository.findByCompanyName(seller.getCompanyName());
            try {
                if (seller1.getCompanyName().equalsIgnoreCase(seller.getCompanyName())) {
                    return "Company name should be unique";
                }
            }
            catch(NullPointerException ex){
                ex.printStackTrace();
            }

        List<Seller> sellers=sellerRepository.findByGst(seller.getGst());
            boolean flag=false;
            for( Seller sellerLoop : sellers)
            {
                if(sellerLoop.getGst().equals(seller.getGst()))
                {
                    flag=true;
                    break;
                }
            }
            try{
                if (flag){
                    return "Gst should be unique";
                }
            }
            catch(NullPointerException ex){
                ex.printStackTrace();
            }
            boolean isValidPassword=ValidPassword.isValidPassword(seller.getPassword());
            if(!isValidPassword){
                return "Password is not valid";
            }

            seller.setPassword(passwordEncoder.encode(seller.getPassword()));

            if(seller.getCompanyContact().length()!=10){
                return "Invalid Contact";
            }

            Role role=new Role();
            role.setAuthority("ROLE_SELLER");
            Set<Role>roleSet=new HashSet<>();
            roleSet.add(role);
            seller.setRoles(roleSet);
            seller.setCreatedBy(seller.getFirstName());
            seller.setDateCreated(new Date());
            seller.setLocked(false);
            seller.setPasswordExpired(false);

            Set<Address> address=seller.getAddresses();
            address.forEach(address1 -> {
            Address address2=address1;
            address2.setUser(seller);});

            userRepository.save(seller);

            return "Success";
    }
}
