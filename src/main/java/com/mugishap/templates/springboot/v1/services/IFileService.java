package com.mugishap.templates.springboot.v1.services;

import com.mugishap.templates.springboot.v1.exceptions.InvalidFileException;
import com.mugishap.templates.springboot.v1.models.File;
import com.mugishap.templates.springboot.v1.enums.EFileStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface IFileService {

        public File getById(UUID id);

        public File create(MultipartFile document, String directory);

        public boolean delete(UUID id);

        public String getFileExtension(String fileName);

        public String handleFileName(String fileName, UUID id) throws InvalidFileException;

        public boolean isValidExtension(String fileName) throws InvalidFileException;

        public File getByName(String filename);
}
