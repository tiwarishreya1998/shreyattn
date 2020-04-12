package com.bootcamp.shoppingApp.Configurations;


import com.bootcamp.shoppingApp.Model.user.User;
import com.bootcamp.shoppingApp.Model.user.UserLoginFailCounter;
import com.bootcamp.shoppingApp.repository.UserLoginFailCounterRepository;
import com.bootcamp.shoppingApp.repository.UserRepository;
import com.bootcamp.shoppingApp.utils.SendEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Optional;

@Component
public class UserLoginFailCounterConfig {

    @Autowired
    private UserLoginFailCounterRepository userLoginFailCounterRepository;

    @Autowired
    private SendEmail sendEmail;


    @Autowired
    private UserRepository userRepository;


    // to set the attempts to login..
    @EventListener
    public void authenticationFailed(AuthenticationFailureBadCredentialsEvent event){
        int counter;
        String userEmail=(String)event.getAuthentication().getPrincipal();//returns string
        Optional<UserLoginFailCounter> userLoginFailCounter=userLoginFailCounterRepository.findByEmail(userEmail);

        if (!userLoginFailCounter.isPresent()){
            UserLoginFailCounter userLoginFailCounter1=new UserLoginFailCounter();
            userLoginFailCounter1.setAttempts(1);
            userLoginFailCounter1.setEmail(userEmail);
            userLoginFailCounterRepository.save(userLoginFailCounter1);
        }
        if(userLoginFailCounter.isPresent()){
            counter=userLoginFailCounter.get().getAttempts();
            System.out.println(counter);

            if (counter>=2){
                User user=userRepository.findByEmail(userEmail);
                user.setLocked(true);
                sendEmail.sendEmail("Account Locked","Your account has been Locked",userEmail);
                userRepository.save(user);

            }

            UserLoginFailCounter userLoginFailCounter1=userLoginFailCounter.get();
            userLoginFailCounter1.setAttempts(counter++);
            userLoginFailCounter1.setEmail(userEmail);
            userLoginFailCounterRepository.save(userLoginFailCounter1);
        }
        }
        //if registered then clear the previous attempts
    @EventListener
    public void authenticationPass(AuthenticationSuccessEvent event){
        LinkedHashMap<String,String> userMap=new LinkedHashMap<>();
        try {
            userMap = (LinkedHashMap<String, String>) event.getAuthentication().getDetails();//return whole object as a map...
        }
        catch(ClassCastException ex){

        }
        String userEmail=new String();
        try{
            userEmail=userMap.get("username");////username is a field..
        }
        catch (NullPointerException ex){

        }
        Optional<UserLoginFailCounter>userLoginFailCounter=userLoginFailCounterRepository.findByEmail(userEmail);
        if (userLoginFailCounter.isPresent()){
            userLoginFailCounterRepository.deleteById(userLoginFailCounter.get().getId());
        }
    }
}
