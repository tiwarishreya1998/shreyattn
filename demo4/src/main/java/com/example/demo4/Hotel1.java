package com.example.demo4;

import org.springframework.stereotype.Component;

@Component
public class Hotel1 implements Hotel{
    @Override
    public void hotelname() {
        System.out.println("The hotel 1 is The Taj");
    }
}
