package com.mugishap.templates.springboot.v1.payload.request;

import com.mugishap.templates.springboot.v1.enums.EGender;
import lombok.Getter;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Getter
public class UpdateUserDTO {
    @Email
    private String email;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @Pattern(regexp = "[0-9]{12}")
    private String telephone;


    private EGender gender;

}