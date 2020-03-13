package com.example.Restful2.service;

import com.example.Restful2.model.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class UserService {
    private static List<User> userList=new ArrayList<>();


    static {
        userList.add(new User("shreya","Haridwar"));
        userList.add(new User("Riya","Delhi"));

    }

    public List<User> getUserList(){
        return userList;
    }

    public List<User> addUser(User user){
        userList.add(user);
        return userList;
    }
    public List<User> deleteUser(String name) {
        Iterator iterator = userList.iterator();
        while(iterator.hasNext()) {
            User userModel = (User) iterator.next();
            if (name.equals(userModel.getName())) {
                userList.remove(userModel);
                return userList;
            }
        }
        return null;
    }

    public static User findOne(String name) {
        for (User user : userList) {
            if (user.getName().equals(name)) {
                return user;
            }
        }
        return null;
    }



}
