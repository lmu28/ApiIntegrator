package com.example.service.runner;


import com.example.service.files.FileBuilder;
import com.example.service.files.impl.DownloadPhotoService;
import com.example.service.files.impl.FileOperations;
import com.example.service.impl.ApplyGfpgan;
import com.example.service.s3.FileLoader;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ApplyRunnerFile implements ApplyRunner{

    @Value("${source.file1}")
    private String filename;

    @Value("${source.download-folder-path}")
    private String destinationToDownload;

    @Value("${source.path-to-save-files}")
    private String pathToProjectData;

    @Value("${source.files-to-upload}")
    private String pathFrom;




    private final FileOperations fileOperations;
    private final ApplyGfpgan singleService;
    private final DownloadPhotoService downloadPhotoService;

    private final FileBuilder fileBuilder;
    private final FileLoader fileLoader;


    @Override
    public void run() {

        Map<String, Object> params = new HashMap<>();
        params.put("scale",(double)1);

        List<String> fileAsList = fileLoader.load(pathFrom);
        String file  = fileBuilder.build(fileAsList, filename, pathToProjectData);
//
//        String file = fileOperations.read(pathToProjectData+File.separator+filename);
//        List<String> fileAsList = fileOperations.process(file);

        List<String> results = singleService.applyAll(fileAsList,params);

//        for(String line: results){
//            System.out.println(line);
//        }


        downloadAll(results);


    }

    private void downloadAll(List<String> results) {
        for (int i = 0; i < results.size(); i++) {
            String photoName = i+".jpg";
            downloadPhotoService.download(results.get(i),destinationToDownload+ File.separator+photoName);


        }
        System.out.println("Download completed");
    }
}
