package com.bootcamp.shoppingApp.Configurations;

import com.bootcamp.shoppingApp.Model.user.User;
import com.bootcamp.shoppingApp.Model.user.UserLoginFailCounter;
import com.bootcamp.shoppingApp.repository.UserLoginFailCounterRepository;
import com.bootcamp.shoppingApp.repository.UserRepository;
import com.bootcamp.shoppingApp.utils.SendEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Optional;
@Component
public class UserLoginEventListener {

    @Autowired
    UserLoginFailCounterRepository userLoginFailCounterRepo;
    @Autowired
    UserRepository userRepo;

    @Autowired
    SendEmail sendEmail;

    private static final Logger LOGGER= LoggerFactory.getLogger(UserLoginEventListener.class);
    @EventListener
    public void authenticationFailed(AuthenticationFailureBadCredentialsEvent event) {
        int counter;
        String userEmail = (String) event.getAuthentication().getPrincipal();
        if ("access-token".contentEquals(userEmail)) {
            LOGGER.debug("Invalid Access token");
            return;
        }
        LOGGER.debug("------{}",userEmail);
        Optional<UserLoginFailCounter> userLoginFailCounter = userLoginFailCounterRepo.findByEmail(userEmail);

        if (!userLoginFailCounter.isPresent()) {
            UserLoginFailCounter userLoginFailCounter1 = new UserLoginFailCounter();
            userLoginFailCounter1.setAttempts(1);
            userLoginFailCounter1.setEmail(userEmail);
            userLoginFailCounterRepo.save(userLoginFailCounter1);
        }
        if (userLoginFailCounter.isPresent()) {
            counter = userLoginFailCounter.get().getAttempts();
            LOGGER.debug("{}",counter);
            if (counter>=2) {
                User user = userRepo.findByEmail(userEmail);
                user.setLocked(true);
                sendEmail.sendEmail("ACCOUNT LOCKED","YOUR ACCOUNT HAS BEEN LOCKED",userEmail);
                userRepo.save(user);
            }
            UserLoginFailCounter userLoginFailCounter1 = userLoginFailCounter.get();
            userLoginFailCounter1.setAttempts(++counter);
            userLoginFailCounter1.setEmail(userEmail);
            LOGGER.debug("--------{}",userLoginFailCounter1);
            userLoginFailCounterRepo.save(userLoginFailCounter1);
        }

    }

    @EventListener
    public void authenticationPass(AuthenticationSuccessEvent event) {
        LinkedHashMap<String,String> userMap = new LinkedHashMap<>();
        try {
            userMap = (LinkedHashMap<String, String>) event.getAuthentication().getDetails();
        } catch (ClassCastException ex) {

        }
        String userEmail = new String();
        try {
            userEmail = userMap.get("username");
        } catch (NullPointerException e) {
            LOGGER.debug("User has successfully login");
        }
        Optional<UserLoginFailCounter> userLoginFailCounter = userLoginFailCounterRepo.findByEmail(userEmail);
        if (userLoginFailCounter.isPresent()){
            userLoginFailCounterRepo.deleteById(userLoginFailCounter.get().getId());
        }
    }
}
