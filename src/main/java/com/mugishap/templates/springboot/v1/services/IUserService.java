package com.mugishap.templates.springboot.v1.services;

import com.mugishap.templates.springboot.v1.enums.ERole;
import com.mugishap.templates.springboot.v1.enums.EUserStatus;
import com.mugishap.templates.springboot.v1.models.File;
import com.mugishap.templates.springboot.v1.models.User;
import com.mugishap.templates.springboot.v1.payload.request.UpdateUserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;


public interface IUserService {

    Page<User> getAll(Pageable pageable,ERole role, String searchKey, EUserStatus status);

    User getById(UUID id);

    User create(User user);
    void save(User user);

    User update(UUID id, UpdateUserDTO dto);

    void delete(UUID id);

    User getLoggedInUser();

    User getByEmail(String email);

    User changeStatus(UUID id, EUserStatus status);

    User changeProfileImage(UUID id, File file);

    User removeProfileImage(UUID id);

    Optional<User> findByActivationCode(String verificationCode);

}