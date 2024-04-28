package com.mugishap.templates.springboot.v1.payload.request;


import com.mugishap.templates.springboot.v1.enums.ERole;
import com.mugishap.templates.springboot.v1.validations.ValidEnum;
import com.mugishap.templates.springboot.v1.validations.ValidPassword;
import com.mugishap.templates.springboot.v1.enums.EGender;
import lombok.Getter;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Getter
public class CreateUserDTO {

    @Email
    private String email;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    @Pattern(regexp = "[0-9]{9,12}", message = "Your phone is not a valid tel we expect 2507***, or 07*** or 7***")
    private String mobile;

    @ValidEnum(enumClass = EGender.class)
    private EGender gender;

    @ValidEnum(enumClass = ERole.class)
    private ERole role;

    @ValidPassword
    private String password;
}
