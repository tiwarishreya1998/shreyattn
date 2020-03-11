package com.example.Restful2.service;

import com.example.Restful2.model.StaticFilterUser;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class StaticFilterService {
    private static List<StaticFilterUser> userList = new ArrayList<>();

    public StaticFilterUser addUser(StaticFilterUser user) {
        userList.add(user);
        return user;
    }
}
