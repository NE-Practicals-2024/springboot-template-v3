package com.mugishap.templates.springboot.v1.services;

import com.mugishap.templates.springboot.v1.payload.request.UpdateUserDTO;
import com.mugishap.templates.springboot.v1.enums.ERole;
import com.mugishap.templates.springboot.v1.enums.EUserStatus;
import com.mugishap.templates.springboot.v1.fileHandling.File;
import com.mugishap.templates.springboot.v1.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;


public interface IUserService {

    public Page<User> getAll(Pageable pageable);

    public User getById(UUID id);

    public User create(User user);

    public User update(UUID id, UpdateUserDTO dto);

    public boolean delete(UUID id);

    public Page<User> getAllByRole(Pageable pageable, ERole role);

    public Page<User> searchUser(Pageable pageable, String searchKey);

    public User getLoggedInUser();

    public User getByEmail(String email);

    public User changeStatus(UUID id, EUserStatus status);

    public User changeProfileImage(UUID id, File file);

    public User removeProfileImage(UUID id);

}