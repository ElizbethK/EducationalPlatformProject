package com.neohackathon.educationalPlatform.service;


import com.neohackathon.educationalPlatform.dto.UserDto;
import com.neohackathon.educationalPlatform.dto.UserRegistrationDto;
import com.neohackathon.educationalPlatform.entity.User;
import com.neohackathon.educationalPlatform.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;



    public User findUserByEmail(String email){
        return userRepository.findByEmail(email);
    }


    public ResponseEntity<String> checkUser(UserDto userDtoJson){
            User user = findUserByEmail(userDtoJson.getEmail());
            if(user == null) {
              return new ResponseEntity<>(
                        "Пользователь не существует",
                        HttpStatus.UNAUTHORIZED);
            } else
               if(!(compareEmailAndPassword(user, userDtoJson))){
                   return new ResponseEntity<>(
                           "Неверный пароль",
                           HttpStatus.UNAUTHORIZED);
               } else return ResponseEntity.ok("ОК! Переход в личный кабинет пользователя платформы");
    }


        public Boolean compareEmailAndPassword(User user, UserDto userDtoJson){
            if((user.getEmail().equals(userDtoJson.getEmail())) & (user.getPassword().equals(userDtoJson.getPassword()))){
               return true;
            }
            return false;
        }




        public ResponseEntity<String> createStudent(UserRegistrationDto userRegistrationDto){
           User user = new User();
           user.setFirstName(userRegistrationDto.getFirstName());
           user.setLastName(userRegistrationDto.getLastName());
           user.setEmail(userRegistrationDto.getEmail());
           user.setPassword(userRegistrationDto.getPassword());
           user.setRole(userRegistrationDto.getRole());
           userRepository.save(user);
           return ResponseEntity.ok("Регистрация прошла успешно");

        }

}
