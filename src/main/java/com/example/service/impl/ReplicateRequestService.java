package com.example.service.impl;

import com.example.service.RequestService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ReplicateRequestService implements RequestService {

    @Value("${replicate.api.token}")
    private String token;





    @Override
    public ResponseEntity<String> get(String url) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("Authorization", String.format("Token %s", token));

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<String> re = restTemplate.exchange(url, HttpMethod.GET,requestEntity,String.class);

        return re;
    }

    @Override
    public ResponseEntity<String> post(String url, String body) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("Authorization", String.format("Token %s", token));

        HttpEntity<String> requestEntity = new HttpEntity<String>(body, headers);

        ResponseEntity<String> re = restTemplate.exchange(url, HttpMethod.POST,requestEntity,String.class);

        return re;
    }



}
