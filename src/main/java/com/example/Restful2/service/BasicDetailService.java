package com.example.Restful2.service;

import com.example.Restful2.model.BasicUser;
import com.example.Restful2.model.DetailedUser;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class BasicDetailService {


    static List<BasicUser> basicUsers = new ArrayList<>();
    static List<DetailedUser> detailedUsers= new ArrayList<>();

    static {
        basicUsers.add(new BasicUser("Shreya","22"));
        detailedUsers.add(new DetailedUser("Shreya","tiwari","Haridwar"));
    }

    public List<BasicUser> getBasicUsers() {
        return basicUsers;
    }


    public List<DetailedUser> getDetailedUsers() {

        return detailedUsers;
    }
}

