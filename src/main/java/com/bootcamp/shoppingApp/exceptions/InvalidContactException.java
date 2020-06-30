package com.bootcamp.shoppingApp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidContactException extends RuntimeException{
    public InvalidContactException(String s){
        super(s);
    }


}
