package com.example.spring_certificate.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDto {
    private String id;
    private String password;
    private String email;
    private String name;
    private String gender;
}
