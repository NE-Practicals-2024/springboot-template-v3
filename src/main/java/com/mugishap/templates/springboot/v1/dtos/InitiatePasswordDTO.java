package com.mugishap.templates.springboot.v1.dtos;

import lombok.Getter;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Getter
public class InitiatePasswordDTO {

    @NotBlank
    @Email
    private String email;
}
