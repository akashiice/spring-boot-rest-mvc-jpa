package com.springboot.rest.springbootrest.controller;

import com.fasterxml.jackson.databind.util.BeanUtil;
import com.springboot.rest.springbootrest.Response.UserResponse;
import com.springboot.rest.springbootrest.dto.UserDto;
import com.springboot.rest.springbootrest.model.UserDetails;
import com.springboot.rest.springbootrest.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    public String getUser() {
        return "get user";
    }

    @PostMapping
    public UserResponse createUser(@RequestBody UserDetails userDetails) {
        UserResponse response = new UserResponse();
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userDetails, userDto);
        UserDto createdUser = userService.createUser(userDto);
        BeanUtils.copyProperties(createdUser, response);
        return response;
    }

    @PutMapping
    public String updateUser() {
        return "update user";
    }

    @DeleteMapping
    public String deleteUser() {
        return "delete user";
    }


}
