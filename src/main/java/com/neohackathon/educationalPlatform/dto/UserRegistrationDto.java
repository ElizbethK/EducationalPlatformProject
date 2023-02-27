package com.neohackathon.educationalPlatform.dto;

import com.neohackathon.educationalPlatform.entity.Role;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserRegistrationDto {
    private String firstName;
    private String lastName;
    private String email;
    private String password;

}
