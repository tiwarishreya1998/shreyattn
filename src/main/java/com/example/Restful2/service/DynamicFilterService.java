package com.example.Restful2.service;

import com.example.Restful2.model.DynamicFilterUser;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class DynamicFilterService {
    private static List<DynamicFilterUser> userList = new ArrayList<>();

    public DynamicFilterUser addUser(DynamicFilterUser user) {
        userList.add(user);
        return user;
    }
}
