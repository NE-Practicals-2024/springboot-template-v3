package com.mugishap.templates.springboot.v1.controllers;

import com.mugishap.templates.springboot.v1.enums.ERole;
import com.mugishap.templates.springboot.v1.exceptions.BadRequestException;
import com.mugishap.templates.springboot.v1.models.File;
import com.mugishap.templates.springboot.v1.models.Role;
import com.mugishap.templates.springboot.v1.models.User;
import com.mugishap.templates.springboot.v1.payload.request.CreateUserDTO;
import com.mugishap.templates.springboot.v1.payload.request.UpdateUserDTO;
import com.mugishap.templates.springboot.v1.payload.response.ApiResponse;
import com.mugishap.templates.springboot.v1.repositories.IRoleRepository;
import com.mugishap.templates.springboot.v1.services.IFileService;
import com.mugishap.templates.springboot.v1.services.IUserService;
import com.mugishap.templates.springboot.v1.utils.Constants;
import com.mugishap.templates.springboot.v1.utils.Utility;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collections;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;
    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private final IRoleRepository roleRepository;
    private final IFileService fileService;

    @Value("${uploads.directory.user_profiles}")
    private String userProfilesDirectory;

    @GetMapping(path = "/current-user")
    public ResponseEntity<ApiResponse> currentlyLoggedInUser() {
        return ResponseEntity.ok(ApiResponse.success("Currently logged in user fetched", userService.getLoggedInUser()));
    }

    @PutMapping(path = "/update")
    public ResponseEntity<ApiResponse> update(@RequestBody UpdateUserDTO dto) {
        User updated = this.userService.update(this.userService.getLoggedInUser().getId(), dto);
        return ResponseEntity.ok(ApiResponse.success("User updated successfully", updated));
    }

    @GetMapping(path = "/all")
    public ResponseEntity<ApiResponse> getAllUsers(
            @RequestParam(value = "page", defaultValue = Constants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = Constants.DEFAULT_PAGE_SIZE) int limit
    ) {
        Pageable pageable = (Pageable) PageRequest.of(page, limit, Sort.Direction.ASC, "id");
        return ResponseEntity.ok(ApiResponse.success("Users fetched successfully", userService.getAll(pageable)));
    }

    @GetMapping(path = "/all/{role}")
    public ResponseEntity<ApiResponse> getAllUsersByRole(
            @RequestParam(value = "page", defaultValue = Constants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = Constants.DEFAULT_PAGE_SIZE) int limit,
            @PathVariable(value = "role") ERole role
    ) {
        Pageable pageable = (Pageable) PageRequest.of(page, limit, Sort.Direction.ASC, "id");
        return ResponseEntity.ok(ApiResponse.success("Users fetched successfully", userService.getAllByRole(pageable, role)));
    }

    @GetMapping(path = "/search")
    public Page<User> searchUsers(
            @RequestParam(value = "page", defaultValue = Constants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = Constants.DEFAULT_PAGE_SIZE) int limit,
            @RequestParam(value = "searchKey") String searchKey
    ) {
        Pageable pageable = (Pageable) PageRequest.of(page, limit, Sort.Direction.ASC, "id");
        return userService.searchUser(pageable, searchKey);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ApiResponse> getById(@PathVariable(value = "id") UUID id) {
        return ResponseEntity.ok(ApiResponse.success("User fetched successfully", this.userService.getById(id)));
    }

    @PostMapping(path = "/register")
    public ResponseEntity<ApiResponse> register(@RequestBody @Valid CreateUserDTO dto) {

        User user = new User();

        String encodedPassword = passwordEncoder.encode(dto.getPassword());
        Role role = roleRepository.findByName(dto.getRole()).orElseThrow(
                () -> new BadRequestException("User Role not set"));

        user.setEmail(dto.getEmail());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setGender(dto.getGender());
        user.setTelephone(dto.getTelephone());
        user.setPassword(encodedPassword);
        user.setRoles(Collections.singleton(role));

        User entity = this.userService.create(user);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().toString());
        return ResponseEntity.created(uri).body(ApiResponse.success("User created successfully", entity));
    }

    @PutMapping(path = "/upload-profile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse> uploadProfileImage(
            @RequestParam("file") MultipartFile document
    ) {
        if (!Utility.isImageFile(document)) {
            throw new BadRequestException("Only image files are allowed");
        }
        User user = this.userService.getLoggedInUser();
        File file = this.fileService.create(document, userProfilesDirectory);

        User updated = this.userService.changeProfileImage(user.getId(), file);

        return ResponseEntity.ok(ApiResponse.success("File saved successfully", updated));

    }

    @PatchMapping(path = "/remove-profile")
    public ResponseEntity<ApiResponse> removeProfileImage() {
        User user = this.userService.getLoggedInUser();
        User updated = this.userService.removeProfileImage(user.getId());
        return ResponseEntity.ok(ApiResponse.success("Profile image removed successfully", updated));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse> deleteMyAccount() {
        User user = this.userService.getLoggedInUser();
        this.userService.delete(user.getId());
        return ResponseEntity.ok(ApiResponse.success("Account deleted successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteByAdmin(
            @PathVariable(value = "id") UUID id
    ) {
        this.userService.delete(id);
        return ResponseEntity.ok(ApiResponse.success("Account deleted successfully"));
    }

}