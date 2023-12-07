package com.example.service.s3;

import java.util.List;

public interface FileLoader {
    // upload all files from the path



    List<String> load(String from);
}
