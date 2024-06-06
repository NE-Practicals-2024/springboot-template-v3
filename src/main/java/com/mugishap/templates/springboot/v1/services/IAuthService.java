package com.mugishap.templates.springboot.v1.services;

import com.mugishap.templates.springboot.v1.payload.request.InitiatePasswordResetDTO;
import com.mugishap.templates.springboot.v1.payload.request.ResetPasswordDTO;
import com.mugishap.templates.springboot.v1.payload.response.JwtAuthenticationResponse;
import com.mugishap.templates.springboot.v1.security.JwtAuthenticationFilter;

public interface IAuthService {
    JwtAuthenticationResponse login(String email, String password);

    void initiateResetPassword(String email);

    void resetPassword(String email, String passwordResetCode, String newPassword);

    void initiateAccountVerification(String email);

    void verifyAccount(String activationCode);
}
