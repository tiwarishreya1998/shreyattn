package com.bootcamp.shoppingApp.Controller;


import com.bootcamp.shoppingApp.Model.Customer;
import com.bootcamp.shoppingApp.Model.Seller;
import com.bootcamp.shoppingApp.Service.UserRegistrationService;
import com.bootcamp.shoppingApp.Utils.SendEmail;
import com.sun.media.jfxmedia.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class UserRegistrationController {

    @Autowired
    private UserRegistrationService userRegistrationService;

    @Autowired
    private SendEmail sendEmail;

    @PostMapping("register/customer")
    public String registerCustomer(@RequestBody Customer customer, HttpServletResponse httpServletResponse){
        String getMessage =userRegistrationService.registerCustomer(customer);


        if(getMessage.equals("Success")){
            httpServletResponse.setStatus(HttpServletResponse.SC_CREATED);
        }
        else{
            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);

        }
        return getMessage;

    }


    @PostMapping("register/seller")
    public String registerSeller(@RequestBody Seller seller,HttpServletResponse httpServletResponse){
        String getMessage =userRegistrationService.registerSeller(seller);
        if(getMessage.equals("Success")){
            sendEmail.sendEmail("Account Created","Waiting for approval",seller.getEmail());
            httpServletResponse.setStatus(HttpServletResponse.SC_CREATED );
        }
        else{
            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        return getMessage;
    }


}
