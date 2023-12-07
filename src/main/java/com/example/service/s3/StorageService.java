package com.example.service.s3;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface StorageService {

    String uploadFile(File file);
}
