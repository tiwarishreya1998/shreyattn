package com.bootcamp.shoppingApp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FieldAlreadyPresent extends RuntimeException {
    public FieldAlreadyPresent(String s){
        super(s);
    }
}
