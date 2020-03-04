package com.example.demo4;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HotelBooking {

    private Hotel hotel;

    @Autowired
    public HotelBooking(Hotel hotel){
        this.hotel=hotel;
    }

    public void hotelType()
    {
        hotel.hotelname();
    }

}