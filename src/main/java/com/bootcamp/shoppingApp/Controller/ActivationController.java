package com.bootcamp.shoppingApp.Controller;

import com.bootcamp.shoppingApp.Model.CustomerActivate;
import com.bootcamp.shoppingApp.Service.CustomerActivateService;
import org.aspectj.weaver.patterns.IToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class ActivationController {

    @Autowired
    private CustomerActivateService customerActivateService;


    @PutMapping("customer/activate")
    public String activateCustomer(@RequestParam String email,String  token, HttpServletResponse httpServletResponse){
        String getMessage=customerActivateService.customerActivate(email,token);
        if(getMessage.equals("Success")){
            httpServletResponse.setStatus(HttpServletResponse.SC_CREATED);
        }
        else{
            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }

        return getMessage;
    }


    @PostMapping("/customer/re-sendActivation")
    public String resendLink(@RequestParam String email,HttpServletResponse httpServletResponse){
        String getMessage= customerActivateService.resendLink(email);
        if(getMessage.equals("Success")){
            httpServletResponse.setStatus(HttpServletResponse.SC_CREATED);
        }
        else{
            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        return  getMessage;
    }

}
