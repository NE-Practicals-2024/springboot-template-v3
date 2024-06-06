package com.mugishap.templates.springboot.v1.controllers;

import com.mugishap.templates.springboot.v1.enums.EUserStatus;
import com.mugishap.templates.springboot.v1.exceptions.AppException;
import com.mugishap.templates.springboot.v1.exceptions.BadRequestException;
import com.mugishap.templates.springboot.v1.models.User;
import com.mugishap.templates.springboot.v1.payload.request.InitiateAccountVerificationDTO;
import com.mugishap.templates.springboot.v1.payload.request.InitiatePasswordResetDTO;
import com.mugishap.templates.springboot.v1.payload.request.LoginDTO;
import com.mugishap.templates.springboot.v1.payload.request.ResetPasswordDTO;
import com.mugishap.templates.springboot.v1.payload.response.ApiResponse;
import com.mugishap.templates.springboot.v1.payload.response.JwtAuthenticationResponse;
import com.mugishap.templates.springboot.v1.security.JwtTokenProvider;
import com.mugishap.templates.springboot.v1.services.IAuthService;
import com.mugishap.templates.springboot.v1.services.IUserService;
import com.mugishap.templates.springboot.v1.standalone.MailService;
import com.mugishap.templates.springboot.v1.utils.Utility;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final IAuthService authService;

    @PostMapping(path = "/login")
    public ResponseEntity<ApiResponse> login(@Valid @RequestBody LoginDTO dto) {
        return ResponseEntity.ok(ApiResponse.success("Login successful", this.authService.login(dto.getEmail(), dto.getPassword())));
    }

    @PostMapping(path = "/initiate-reset-password")
    public ResponseEntity<ApiResponse> initiateResetPassword(@RequestBody @Valid InitiatePasswordResetDTO dto) {
        this.authService.initiateResetPassword(dto.getEmail());
        return ResponseEntity.ok(ApiResponse.success("Please check your mail and activate account"));
    }


    @PostMapping(path = "/reset-password")
    public ResponseEntity<ApiResponse> resetPassword(@RequestBody @Valid ResetPasswordDTO dto) {
      this.authService.resetPassword(dto.getEmail(),dto.getPasswordResetCode(), dto.getNewPassword());
        return ResponseEntity.ok(ApiResponse.success("Password successfully reset"));
    }

    @PutMapping("/initiate-account-verification")
    private ResponseEntity<ApiResponse> initiateAccountVerification(@RequestBody @Valid InitiateAccountVerificationDTO dto) {
        this.authService.initiateAccountVerification(dto.getEmail());
        return ResponseEntity.ok(ApiResponse.success("Verification code sent to email, expiring in 6 hours"));
    }

    @PatchMapping("/verify-account/{verificationCode}")
    private ResponseEntity<ApiResponse> verifyAccount(
            @PathVariable("verificationCode") String verificationCode
    ) {
        this.authService.verifyAccount(verificationCode);
        return ResponseEntity.ok(ApiResponse.success("Account verified successfully, you can now login"));
    }
}