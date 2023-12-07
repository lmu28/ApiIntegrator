package com.example.service.s3.impl;

import com.example.service.s3.FileLoader;
import com.example.service.s3.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class FileLoaderS3 implements FileLoader {

    private final StorageService storageService;





    @Override
    public List<String> load(String from) {
        List<String>  urls = new ArrayList<>();
        File folder = new File(from);
        File[] files = folder.listFiles();

        for (File file : files) {
            if (file.isFile()) {
                urls.add(storageService.uploadFile(new File(file.getAbsolutePath())));
            }
        }


        return urls;
    }
}
