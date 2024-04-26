package com.mugishap.templates.springboot.v1.dtos;


import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Getter
public class SignInDTO {

    @NotBlank
    @Email
    private  String email;

    @NotBlank
    private  String password;
}

