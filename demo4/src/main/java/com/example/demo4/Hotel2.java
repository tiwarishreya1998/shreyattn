package com.example.demo4;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class Hotel2 implements Hotel {
    @Override
    public void hotelname() {
        System.out.println("The hotel 2 was The Marriott");
    }
}
