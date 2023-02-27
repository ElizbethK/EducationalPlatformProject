package com.neohackathon.educationalPlatform.controller;

import com.neohackathon.educationalPlatform.dto.EmailDto;
import com.neohackathon.educationalPlatform.dto.UserDto;
import com.neohackathon.educationalPlatform.dto.UserRegistrationDto;
import com.neohackathon.educationalPlatform.entity.User;
import com.neohackathon.educationalPlatform.exception.ResourceNotFoundException;
import com.neohackathon.educationalPlatform.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    private UserService userService;

    @Autowired
    public LoginController(UserService studentService) {
        this.userService = studentService;
    }


    @GetMapping
    public String test(){
        return "hello";
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserDto userDtoJson){
        return userService.checkUser(userDtoJson);
    }

    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody UserRegistrationDto userRegistrationDtoJson){
        return userService.createUser(userRegistrationDtoJson);
    }

    @PostMapping("/forget")
    public ResponseEntity<String> getPassword(@RequestBody EmailDto emailDtoJson){
        User user = userService.findUserByEmail(emailDtoJson.getEmail());
        if (user == null) {
            return new ResponseEntity<>(
                   " The user " + emailDtoJson.getEmail() + " does not exist",
                    HttpStatus.UNAUTHORIZED);
        } else {
            return ResponseEntity.ok("The password for " + emailDtoJson.getEmail() +
                    ": "+ user.getPassword());
        }

    }





}
