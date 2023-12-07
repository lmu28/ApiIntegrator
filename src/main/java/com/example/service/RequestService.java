package com.example.service;

import org.springframework.http.ResponseEntity;

public interface RequestService {
    ResponseEntity<String> get(String url);
    ResponseEntity<String> post(String url, String body);
}
