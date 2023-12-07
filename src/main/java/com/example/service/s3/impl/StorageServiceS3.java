package com.example.service.s3.impl;

import com.example.service.s3.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.File;

@Service
@RequiredArgsConstructor
public class StorageServiceS3 implements StorageService {

    @Value("${application.bucket.name}")
    private String bucketName;

    private final S3Client s3Client;


    @Override
    public String uploadFile(File fileObj) {
        String fileName = System.currentTimeMillis() + "_" + fileObj.getName();
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .build();

        PutObjectResponse response = s3Client.putObject(request, RequestBody.fromFile(fileObj));
        String fileUrl = s3Client.utilities().getUrl(builder -> builder.bucket(bucketName).key(fileName)).toExternalForm();
        return fileUrl;
    }


//    public byte[] downloadFile(String fileName) {
//        S3Object s3Object = s3Client.getObject(bucketName, fileName);
//        S3ObjectInputStream inputStream = s3Object.getObjectContent();
//        try {
//            byte[] content = IOUtils.toByteArray(inputStream);
//            return content;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//
//    public String deleteFile(String fileName) {
//        s3Client.deleteObject(bucketName, fileName);
//        return fileName + " removed ...";
//    }

}
