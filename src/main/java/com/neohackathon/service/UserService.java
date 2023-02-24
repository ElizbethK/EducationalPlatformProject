package com.neohackathon.service;


import com.neohackathon.dto.UserDto;
import com.neohackathon.entity.User;
import com.neohackathon.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;


        public User findStudentByEmail(UserDto userJson){
           return userRepository.findByEmail(userJson.getEmail());

        }



        public ResponseEntity<User> createStudent(UserDto userJson){
           User student = new User();
           student.setEmail(userJson.getEmail());
           student.setPassword(userJson.getPassword());
           userRepository.save(student);
           return new ResponseEntity<>(student, HttpStatus.OK);
        }

}
