package com.bootcamp.shoppingApp.Service;


import com.bootcamp.shoppingApp.Model.ForgotPasswordToken;
import com.bootcamp.shoppingApp.Model.User;
import com.bootcamp.shoppingApp.Repository.ForgotPasswordTokenRepo;
import com.bootcamp.shoppingApp.Repository.UserRepository;
import com.bootcamp.shoppingApp.Utils.SendEmail;
import com.bootcamp.shoppingApp.Utils.ValidEmail;
import com.bootcamp.shoppingApp.Utils.ValidPassword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class ForgotPasswordTokenService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SendEmail sendEmail;

    @Autowired
    private ForgotPasswordTokenRepo forgotPasswordTokenRepo;


    PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();

    public String sendToken(String email){
        boolean isValidEmail=ValidEmail.checkEmailValid(email);
        if(!isValidEmail){
            return "Email is not valid";
        }

        User user=userRepository.findByEmail(email);
        try{
            if(user.getEmail().equals(null)){
        }}
        catch(NullPointerException ex){
            return "Email not found";
        }
        String token= UUID.randomUUID().toString();

        ForgotPasswordToken forgotPasswordToken=new ForgotPasswordToken();
        forgotPasswordToken.setExpiryDate(new Date());
        forgotPasswordToken.setToken(token);
        forgotPasswordToken.setUserEmail(email);

        forgotPasswordTokenRepo.save(forgotPasswordToken);


        sendEmail.sendEmail("Forgot Password",token,email);

        return "Success";
    }

    public String resetPassword(String email,String token,String pass,String cPass){
        if(!pass.equals(cPass)){
            return "The password and confirm password are bot same";
        }
        if (!ValidPassword.isValidPassword(pass)){
            return "Password you entered is not valid";
        }
        ForgotPasswordToken  forgotPasswordToken=forgotPasswordTokenRepo.findByUserEmail(email);
        try{
            if(forgotPasswordToken.getUserEmail().equals(null)){

            }
        }
        catch (NullPointerException ex){

            return "No email is found";

        }
        Date date=new Date();
        long difference=date.getTime()-forgotPasswordToken.getExpiryDate().getTime();
        long diffHours=difference/(60*60*1000);
        if (diffHours>24){
            forgotPasswordTokenRepo.deleteByUserEmail(email);
            return "Token has expired";
        }
        if (!forgotPasswordToken.getToken().equals(token)){
            return "Invalid Token";

        }
        if(forgotPasswordToken.getToken().equals(token)){
            User user=userRepository.findByEmail(email);
            user.setPassword(passwordEncoder.encode(pass));
            userRepository.save(user);
            forgotPasswordTokenRepo.deleteByUserEmail(email);
            sendEmail.sendEmail("Password  changed", "Your password has been changed",email);
            return "Success";


        }

        return "Success";



    }


}
