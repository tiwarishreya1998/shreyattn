package com.example.demo3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

//Controller
@RestController

public class Ques1 {


    @Autowired
    private MessageSource messageSource;

    //hard coding
    @GetMapping(path = "/spring-boot")          //mapping return type of method to the provided Uri
    public String myMethod(){
        return "Welcome to Spring boot";
    }



    //Creating bean
    @GetMapping(path = "spring-boot-bean")
    public HelloBean myMethod2()
    {
        return new HelloBean("Welcome to spring boot");
    }




    //using path variable
    ///spring-boot/path-variable/boot
    @GetMapping(path = "spring-boot/path-variable/{name}")
    public HelloBean myMethod3(@PathVariable String name)
    {
        return new HelloBean(String.format("Welcome to spring%s",name));
    }



}