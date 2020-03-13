package com.example.Restful2.controller;


import com.example.Restful2.model.StaticFilterUser;
import com.example.Restful2.service.StaticFilterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StaticFilterController {
    @Autowired
private StaticFilterService staticFilterService;
    @PostMapping(path = "/staticadduser")
    public StaticFilterUser addUser(@RequestBody StaticFilterUser user) {

        return staticFilterService.addUser(user);
    }
}
