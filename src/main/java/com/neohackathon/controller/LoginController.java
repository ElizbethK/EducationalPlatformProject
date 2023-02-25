package com.neohackathon.controller;

import com.neohackathon.dto.UserDto;
import com.neohackathon.entity.User;
import com.neohackathon.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class LoginController {

    private UserService userService;

    @Autowired
    public LoginController(UserService studentService) {
        this.userService = studentService;
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody UserDto userDtoJson){
        User student = userService.findStudentByEmail(userDtoJson);
        if (student == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(student, HttpStatus.OK);
        }
    }

    @PostMapping("/create")
    public void create(@RequestBody UserDto userDtoJson){
        userService.createStudent(userDtoJson);
    }

    @GetMapping("/recover")
    public String recoverPassword(@RequestBody UserDto userDtoJson){
        User student = userService.findStudentByEmail(userDtoJson);
        if (student == null) {
           return "User with email: " + userDtoJson.getEmail() + " not found";
        } else {
            return student.getPassword();
        }
    }



}
