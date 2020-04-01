package com.bootcamp.shoppingApp.Service;

import com.bootcamp.shoppingApp.Model.*;
import com.bootcamp.shoppingApp.Repository.CustomerActivateRepo;
import com.bootcamp.shoppingApp.Repository.SellerRepository;
import com.bootcamp.shoppingApp.Repository.UserRepository;
import com.bootcamp.shoppingApp.Utils.SendEmail;
import com.bootcamp.shoppingApp.Utils.ValidEmail;
import com.bootcamp.shoppingApp.Utils.ValidGst;
import com.bootcamp.shoppingApp.Utils.ValidPassword;
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
    private ValidGst validGst;
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
        boolean isValidGst=validGst.checkGstValid(seller.getGst());
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
            catch (NullPointerException n){

            }

            Seller seller1=sellerRepository.findByCompanyName(seller.getCompany_name());
            try {
                if (seller1.getCompany_name().equalsIgnoreCase(seller.getCompany_name())) {
                    return "Company name should be unique";
                }
            }
            catch(NullPointerException ex){

            }

        List<Seller> sellers=sellerRepository
                .findByGst(seller.getGst());
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

            }
            boolean isValidPassword=ValidPassword.isValidPassword(seller.getPassword());
            if(!isValidPassword){
                return "Password is not valid";
            }

            seller.setPassword(passwordEncoder.encode(seller.getPassword()));

            if(seller.getCompany_contact().length()!=10){
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
            address.forEach(address1 -> {Address address2=address1;
            address2.setUser(seller);});

            userRepository.save(seller);

            return "success";
    }
}
