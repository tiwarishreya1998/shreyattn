package com.example.Restful2.controller;


import com.example.Restful2.model.User;
import com.example.Restful2.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/userQues2")
    @ApiOperation(value = "AddAUser", nickname = "addAUser")
    public void postMethod(@RequestBody User user){
        userService.addUser(user);

    }


    @GetMapping("/userQues2_get")
    @ApiOperation(value = "getAllUsers", nickname = "getAll")
    public List<User> getUser(){
        return userService.getUserList();
    }



    @DeleteMapping(path = "/deleteuser/{name}")
    @ApiOperation(value = "deleteAUser", nickname = "deleteAUser")
    public List<User> deleteUser(@PathVariable String name){
        return userService.deleteUser(name);
    }


    //HateOS Question 11
    @GetMapping("/users/{name}")
    public EntityModel<User> getAUser(@PathVariable String name) {
        User user =  UserService.findOne(name);

        EntityModel<User> resource = new EntityModel(user);

        WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).getUser());
        resource.add(linkTo.withRel("all-users"));
        return resource;
    }
}
