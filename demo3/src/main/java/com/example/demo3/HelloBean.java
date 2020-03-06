package com.example.demo3;

public class HelloBean {
    private final String message;

    public HelloBean(String message) {
        this.message=message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "HelloBean{" +
                "message='" + message + '\'' +
                '}';
    }


}
