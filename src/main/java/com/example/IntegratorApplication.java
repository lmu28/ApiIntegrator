package com.example;

import com.example.service.s3.StorageService;
import com.example.service.runner.ApplyRunnerFile;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

@SpringBootApplication
public class IntegratorApplication {

	public static void main(String[] args) {
		var  a  = SpringApplication.run(IntegratorApplication.class, args);

		var runner = a.getBean(ApplyRunnerFile.class);

		var storage = a.getBean(StorageService.class);

		File file = new File("src/main/resources/data/test.txt");

		storage.uploadFile(file);

		runner.run();











	}

}
