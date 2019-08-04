package com.springboot.rest.springbootrest.utils;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class Utility {

    public String generateUserID() {
        return UUID.randomUUID().toString();
    }

}
