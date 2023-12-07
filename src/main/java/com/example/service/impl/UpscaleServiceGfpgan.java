package com.example.service.impl;

import com.example.service.UpscaleService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
@Getter
@Setter
@RequiredArgsConstructor
public class UpscaleServiceGfpgan implements UpscaleService {

    public static final int MAX_WAITING_TIME = 150;
    private final ReplicateRequestService replicateRequestService;


    @Value("${replicate.api.gfpgan.version}")
    private String apiVersion;
    @Value("${replicate.api.gfpgan.ai.version}")
    private String aiVersion;
    @Value("${replicate.api.url}")
    private String url;





    @Override
    public String upscale(String photoUrl, double scale) {
        String body = buildGfpganBody(photoUrl,scale);
        ResponseEntity<String> postResponse = replicateRequestService.post(url,body);
        if(postResponse.getStatusCode().is2xxSuccessful()){

            String postBody = postResponse.getBody();
            String getUrl = getUrl(postBody);
            ResponseEntity<String> getResponse = replicateRequestService.get(getUrl);
            getResponse = waitForCompleting(getResponse);
            if (!getResponse.getStatusCode().is2xxSuccessful()){
                System.out.println("Error get request");
                return "";
            }
            JsonMapper mapper = new JsonMapper();
            String status = "";
            try {
                status = mapper.readTree(getResponse.getBody()).get("status").asText();
            } catch (JsonProcessingException e) {
                System.out.println(e.getMessage());
            }
            if ("succeeded".equals(status)){
                String getBody = getResponse.getBody();
                return getUpscaledPhotoUrl(getBody);
            }

        }else {
            System.out.println("Error get request");
//            throw new RuntimeException("Error post request");
        }

        return "";
    }

    private ResponseEntity<String> waitForCompleting(ResponseEntity<String> response){

        JsonMapper mapper = new JsonMapper();

        int waitingTime = 0;
        try {
            JsonNode responseJson = mapper.readTree(response.getBody());
            while (!"succeeded".equals(responseJson.get("status").asText())) {
                Thread.sleep(2000);
                waitingTime+=2;
                response = replicateRequestService.get(responseJson.get("urls").get("get").asText());
                if (waitingTime > MAX_WAITING_TIME) return response;
                responseJson = mapper.readTree(response.getBody());
            }
            return response;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


    }


    private String buildGfpganBody(String photoUrl, double scale) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode input = objectMapper.createObjectNode();
        input.put("img", photoUrl);
        input.put("scale", scale);
        input.put("version", aiVersion);
        ObjectNode body = objectMapper.createObjectNode();

        body.put("version", apiVersion);
        body.set("input", input);

        return body.toString();
    }


    private String getUrl(String body){
        ObjectMapper objectMapper = new ObjectMapper();


        JsonNode bodyJson = null;
        try {
            bodyJson = objectMapper.readTree(body);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        String getUrl = bodyJson.get("urls").get("get").asText();

        return getUrl;

    }

    private String getUpscaledPhotoUrl(String body){
        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode bodyJson = null;
        try {
            bodyJson = objectMapper.readTree(body);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        String url = bodyJson.get("output").asText();

        return url;
    }

}
