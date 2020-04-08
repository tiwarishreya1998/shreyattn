package com.bootcamp.shoppingApp.Service;

import com.bootcamp.shoppingApp.Model.CustomerActivate;
import com.bootcamp.shoppingApp.Model.User;
import com.bootcamp.shoppingApp.Repository.CustomerActivateRepo;
import com.bootcamp.shoppingApp.Repository.UserRepository;
import com.bootcamp.shoppingApp.Utils.SendEmail;
import com.bootcamp.shoppingApp.Utils.ValidEmail;
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
    public String customerActivate(String email, String token) {
        boolean isValidEmail = ValidEmail.checkEmailValid(email);
        if (!isValidEmail) {
            return "Email is not valid";

        }
        CustomerActivate customerActivate = customerActivateRepo.findByUserEmail(email);

        try {
            if (customerActivate.getUserEmail().equals(null)) {
            }
        } catch (NullPointerException ex) {
            return "No email found";
        }

        if (!customerActivate.getToken().equals(token)) {
            return "Token is invalid";
        }

        Date date = new Date();
        long diff = date.getTime() - customerActivate.getExpiryDate().getTime();
        long diffHours = diff / (60 * 60 * 1000);

        if (diffHours > 24) {
            customerActivateRepo.deleteByUserEmail(email);

            String newToken = UUID.randomUUID().toString();

            CustomerActivate customerActivate1 = new CustomerActivate();
            customerActivate1.setExpiryDate(new Date());
            customerActivate1.setUserEmail(email);
            customerActivate1.setToken(newToken);

            customerActivateRepo.save(customerActivate1);

            sendEmail.sendEmail("Re-account activate token", newToken, email);


            return "Token has expired";
        }

        if (customerActivate.getToken().equals(token)) {
            User user = userRepository.findByEmail(email);
            user.setActive(true);
            userRepository.save(user);
            sendEmail.sendEmail("Account activated", "Your Account has been activated", email);
            customerActivateRepo.deleteByUserEmail(email);

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

            sendEmail.sendEmail("Re-account activate token",token,email);

            return "Success";


        }
        return "Success";
    }

}