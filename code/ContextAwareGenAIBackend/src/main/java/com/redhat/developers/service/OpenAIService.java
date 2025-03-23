package com.redhat.developers.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
@Service
public class OpenAIService {

  //  @Value("${openai.api.url}")
    private String openAiUrl = "https://api.together.xyz/v1/chat/completions";

    //@Value("${openai.api.key}")
    private String openAiKey = "878502523d92a65458d0269edce48e4df146b85545ee367e1081ea57f3f88e6b";

    private final RestTemplate restTemplate = new RestTemplate();

    public Map<String, String> generateTestCases(String description) {
    	System.out.println("Desc "+description);
        try {
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("model", "meta-llama/Llama-3.3-70B-Instruct-Turbo");
            requestBody.put("messages", new Object[]{Map.of("role", "user", "content", "Generate test cases for: " + description)});
          
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + openAiKey);
            headers.set("Content-Type", "application/json");

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
            ResponseEntity<Map> response = restTemplate.exchange(openAiUrl, HttpMethod.POST, entity, Map.class);
            return Map.of("testCases", response.getBody().toString());
        } catch (Exception e) {
            return Map.of("error", e.getMessage());
        }
    }
}