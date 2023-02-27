package com.neohackathon.educationalPlatform.service;


import com.neohackathon.educationalPlatform.controller.LoginController;
import com.neohackathon.educationalPlatform.dto.UserDto;
import com.neohackathon.educationalPlatform.dto.UserRegistrationDto;
import com.neohackathon.educationalPlatform.entity.Role;
import com.neohackathon.educationalPlatform.entity.User;
import com.neohackathon.educationalPlatform.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;


@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    private UserRepository userRepository;



    public User findUserByEmail(String email){
        return userRepository.findByEmail(email);
    }


    public ResponseEntity<String> checkUser(UserDto userDtoJson){
            User user = findUserByEmail(userDtoJson.getEmail());
            if(user == null) {
              return new ResponseEntity<>(
                        "The user " + userDtoJson.getEmail() + " does not exist",
                        HttpStatus.UNAUTHORIZED);
            } else
               if(!(compareEmailAndPassword(user, userDtoJson))){
                   return new ResponseEntity<>(
                           "Invalid password for the user " + userDtoJson.getEmail() ,
                           HttpStatus.UNAUTHORIZED);
               } else if((compareEmailAndPassword(user, userDtoJson))){
                   return ResponseEntity.ok("Successfully authorizing! " + '\n' +
                       "Hello, " + user.getLastName() +" " + user.getFirstName() + "!");
               } else return new ResponseEntity<>(
                       "Sorry, the error on the server side. We'll fix it soon " ,
                       HttpStatus.INTERNAL_SERVER_ERROR);
    }


        public Boolean compareEmailAndPassword(User user, UserDto userDtoJson){
            if((user.getEmail().equals(userDtoJson.getEmail())) & (user.getPassword().equals(userDtoJson.getPassword()))){
               return true;
            }
            return false;
        }


        public ResponseEntity<String> createUser(UserRegistrationDto userRegistrationDto){
            logger.info("Was sent {} ", userRegistrationDto);
        //   User user = findUserByEmail(userRegistrationDto.getEmail());
         //   logger.info("Was found {}", user);

            if(findUserByEmail(userRegistrationDto.getEmail()) == null){
                addUserToBase(userRegistrationDto);
                return ResponseEntity.ok("Registration completed successfully! " +
                       userRegistrationDto.getLastName() + " " + userRegistrationDto.getFirstName() + ", Welcome!");
            } else
                return new ResponseEntity<>( " The user " + userRegistrationDto.getEmail() +
                        " already exists. Please, go the login form.", HttpStatus.CONFLICT);


                /*return new ResponseEntity<>("No " +
                    user.getLastName() + " " + user.getFirstName(), HttpStatus.CONFLICT);*/


            /*if(!(user == null)) {
                return new ResponseEntity<>(" The user " + userRegistrationDto.getEmail() +
                        " already exists. Please, go the login form.",
                        HttpStatus.CONFLICT);
            } else {
                 addUserToBase(userRegistrationDto);
                return new ResponseEntity<>("Registration completed successfully!" + '\n' +
                        "Welcome, " + user.getLastName() + " " + user.getFirstName() + "!", HttpStatus.INTERNAL_SERVER_ERROR);
            }*/
        }


        public User addUserToBase(UserRegistrationDto userRegistrationDto){
            return userRepository.save(new User(
                        (userRegistrationDto.getFirstName()),
                        (userRegistrationDto.getLastName()),
                        (userRegistrationDto.getEmail()),
                        (userRegistrationDto.getPassword()),
                      determineRole(userRegistrationDto)));
        }


    public Role determineRole(UserRegistrationDto userRegistrationDto){
        String adminEmail = "admin";
        String adminPassword = "admin";

        if (userRegistrationDto.getEmail().equals(adminEmail) & userRegistrationDto.getPassword().equals(adminPassword)) {
            return Role.ADMIN;
        } else return Role.USER;

    }

        public ResponseEntity<String> recoverPassword(@RequestBody String email){
            User user = findUserByEmail(email);
            if (user == null) {
                return new ResponseEntity<>(" The user " + email + " does not exist",
                        HttpStatus.UNAUTHORIZED);
            } else if (!(user == null))  {
                return ResponseEntity.ok("The password for " + email +
                        ": "+ user.getPassword());
            } else return new ResponseEntity<>(
                    "Sorry, the error on the server side. We'll fix it soon " ,
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

}
