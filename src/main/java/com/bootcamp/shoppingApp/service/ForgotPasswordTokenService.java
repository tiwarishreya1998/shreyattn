package com.bootcamp.shoppingApp.service;

import com.bootcamp.shoppingApp.Model.user.ForgotPasswordToken;
import com.bootcamp.shoppingApp.Model.user.User;
import com.bootcamp.shoppingApp.repository.ForgotPasswordTokenRepo;
import com.bootcamp.shoppingApp.repository.UserRepository;
import com.bootcamp.shoppingApp.utils.SendEmail;
import com.bootcamp.shoppingApp.utils.ValidEmail;
import com.bootcamp.shoppingApp.utils.ValidPassword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
            if(user.getEmail()==null){
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

    @Transactional
    public String resetPassword(String email,String token,String pass,String cpass){
        if(!pass.equals(cpass)){
            return "The password and confirm password are both same";
        }
        if (!ValidPassword.isValidPassword(pass)){
            return "Password you entered is not valid";
        }
        ForgotPasswordToken forgotPasswordToken=forgotPasswordTokenRepo.findByUserEmail(email);
        try{
            if(forgotPasswordToken.getUserEmail()==null){
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
