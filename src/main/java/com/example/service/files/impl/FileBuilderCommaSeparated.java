package com.example.service.files.impl;

import com.example.service.files.FileBuilder;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;


@Service
public class FileBuilderCommaSeparated implements FileBuilder {


    @Override
    public String build(List<String> elements, String name, String to) {
        StringBuilder fileData = new StringBuilder();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(to + File.separator + name))) {
            for (int i = 0; i < elements.size(); i++) {
                String item = elements.get(i);
                writer.write(item);
                fileData.append(item);
                if (i != elements.size() - 1) {
                    writer.write(",");
                    fileData.append(",");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileData.toString();
    }
}
