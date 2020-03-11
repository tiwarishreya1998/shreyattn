package com.example.Restful2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Ques1 {

    @Autowired
   private MessageSource messageSource;

    @GetMapping("/greetings/part1")
    public String  myMethod(String username){
        return messageSource.getMessage("greetings.message",null, LocaleContextHolder.getLocale());
    }
    @GetMapping("/greetings/part2")
    public String  myMethod2(@RequestParam("username") String username){
        return messageSource.getMessage("greetings.message",null, LocaleContextHolder.getLocale())+" "+username;
    }

}
