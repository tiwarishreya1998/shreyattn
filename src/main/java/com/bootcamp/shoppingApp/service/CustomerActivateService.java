package com.bootcamp.shoppingApp.service;


import com.bootcamp.shoppingApp.Model.user.CustomerActivate;
import com.bootcamp.shoppingApp.Model.user.User;
import com.bootcamp.shoppingApp.repository.CustomerActivateRepo;
import com.bootcamp.shoppingApp.repository.UserRepository;
import com.bootcamp.shoppingApp.utils.SendEmail;
import com.bootcamp.shoppingApp.utils.ValidEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.UUID;

@Service
public class CustomerActivateService {
    @Autowired
    private SendEmail sendEmail;
    @Autowired
    private CustomerActivateRepo customerActivateRepo;
    @Autowired
    private UserRepository userRepository;


    @Transactional
    public String activateCustomer( String token) {
        CustomerActivate customerActivate = customerActivateRepo.findByToken(token);

        if(customerActivate==null){
            return "invalid token";
        }

        Date date = new Date();
        long diff = date.getTime() - customerActivate.getExpiryDate().getTime();
        long diffHours = diff / (60 * 60 * 1000);
        //token expires case
        if (diffHours > 24) {
            customerActivateRepo.deleteByUserEmail(customerActivate.getUserEmail());

            String newToken = UUID.randomUUID().toString();

            CustomerActivate customerActivate1 = new CustomerActivate();
            customerActivate1.setExpiryDate(new Date());
            customerActivate1.setUserEmail(customerActivate.getUserEmail());
            customerActivate1.setToken(newToken);

            customerActivateRepo.save(customerActivate1);

            sendEmail.sendEmail("Re-account activate token","http://localhost:8080/customer/activate/"+newToken, customerActivate.getUserEmail());
            return "Token has expired";
        }

        if (customerActivate.getToken().equals(token)) {
            User user = userRepository.findByEmail(customerActivate.getUserEmail()
            );
            user.setActive(true);
            userRepository.save(user);
            sendEmail.sendEmail("Account activated", "Your Account has been activated", customerActivate.getUserEmail());
            customerActivateRepo.deleteByUserEmail(customerActivate.getUserEmail());

            return "Success";
        }
        return "Success";
    }

    @Transactional
    public String resendLink(String email) {
        if (!ValidEmail.checkEmailValid(email)) {
            return "Email is invalid";
        }

        User user = userRepository.findByEmail(email);
        try {
            if (user.getEmail().equals(null)) {

            }
        } catch (NullPointerException ex) {

            return "no email found";
        }

        if (user.isActive()){
            return "User is already active";
        }

        if(!user.isActive()){

            customerActivateRepo.deleteByUserEmail(email);

            String token=UUID.randomUUID().toString();

            CustomerActivate localCustomerActivate=new CustomerActivate();
            localCustomerActivate.setToken(token);
            localCustomerActivate.setUserEmail(email);
            localCustomerActivate.setExpiryDate(new Date());

            customerActivateRepo.save(localCustomerActivate);

            sendEmail.sendEmail("Re-account activate token","http://localhost:8080/customer/activate/"+token,email);

            return "Success";
        }
        return "Success";
    }

}