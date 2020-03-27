package com.bootcamp.shoppingApp.Security;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;


public class GrantAuthorityImpl implements GrantedAuthority {


    private String authority;

    public GrantAuthorityImpl(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return authority;
    }

    @Override
    public String toString() {
        return "GrantAuthorityImpl{" +
                "authority='" + authority + '\'' +
                '}';
    }
}