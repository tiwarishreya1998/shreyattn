package com.bootcamp.shoppingApp.Configurations;

import com.bootcamp.shoppingApp.utils.SendEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SchedulingConfig {
    @Autowired
    private SendEmail sendEmail;

    @Scheduled(cron = "0 0 0 * * *",zone = "Indian/Maldives")
    public void mailToSeller(){
        sendEmail.sendEmail("Order accept or Reject","Please the details of order accepted and rejected","sellerEmail");
    }

}
