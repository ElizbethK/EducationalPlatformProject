package com.neohackathon.educationalPlatform.controller;

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

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserDto userDtoJson) throws ResourceNotFoundException {
        return userService.checkUser(userDtoJson);
    }

    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody UserRegistrationDto userRegistrationDto){
        return userService.createStudent(userRegistrationDto);
    }

    @PostMapping("/recover")
    public ResponseEntity<String> recoverPassword(@RequestBody String email){
        User user = userService.findUserByEmail(email);
        if (user == null) {
            return new ResponseEntity<>(
                    "Пользователь не существует",
                    HttpStatus.UNAUTHORIZED);
        } else {
            return ResponseEntity.ok("Пароль для пользователя " + email +
                    ": "+ user.getPassword());
        }

    }





}
