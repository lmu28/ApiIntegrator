package com.example.service.files.impl;

import com.example.service.files.FileProcessor;
import com.example.service.files.FileRead;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


@Service
public class FileOperations implements FileProcessor, FileRead {




    @Override
    public List<String> process(String file) {
        List<String> fileAsList =Arrays.asList(file.split(","));
        return fileAsList;
    }

    @Override
    public String read(String path) {
        String file;
        try {
            Path filePath = Paths.get(path);
            file = new String(Files.readAllBytes(filePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return file;
    }
}
