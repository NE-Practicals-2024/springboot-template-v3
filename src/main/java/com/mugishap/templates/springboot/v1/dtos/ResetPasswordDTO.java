package com.mugishap.templates.springboot.v1.dtos;

import com.mugishap.templates.springboot.v1.security.ValidPassword;
import lombok.Getter;

import jakarta.validation.constraints.NotBlank;

@Getter
public class ResetPasswordDTO {

    @NotBlank
    private String email;

    @NotBlank
    private String activationCode;

    @ValidPassword
    private String password;
}
