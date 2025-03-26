package com.wf.contextaware.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wf.contextaware.model.TestCase;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

@Service
public class OpenAIService {

  //  @Value("${openai.api.url}")
    private String openAiUrl = "https://api.together.xyz/v1/chat/completions";

    //@Value("${openai.api.key}")
    private String openAiKey = "878502523d92a65458d0269edce48e4df146b85545ee367e1081ea57f3f88e6b";

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();
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
    
    public Map<String, String> generateBDDTestCases(String url, String context) {
    	System.out.println("Desc "+context);
        try {
            Map<String, Object> requestBody = new HashMap<>();
            String prompt = "Generate BDD test cases for URL: " + url + " with context: " + context;
            requestBody.put("model", "meta-llama/Llama-3.3-70B-Instruct-Turbo");
            requestBody.put("messages", new Object[]{Map.of("role", "user", "content", prompt)});
          
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
    
    public List<TestCase> generateTestCases(String url, String context) {
    	System.out.println("Desc "+context);
        try {
            Map<String, Object> requestBody = new HashMap<>();
            String prompt = "Generate BDD test cases for URL: " + url + " with context: " + context;

            requestBody.put("model", "meta-llama/Llama-3.3-70B-Instruct-Turbo");
            requestBody.put("messages", new Object[]{Map.of("role", "user", "content", prompt)});
          
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + openAiKey);
            headers.set("Content-Type", "application/json");

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
            ResponseEntity<String> response = restTemplate.exchange(openAiUrl, HttpMethod.POST, entity, String.class);
            
            
            
            JsonNode jsonNode = objectMapper.readTree(response.getBody());
            String testCaseText = jsonNode.path("choices").get(0).path("message").path("content").asText();

            List<TestCase> testCases = new ArrayList<>();
            String[] lines = testCaseText.split("\n");

            for (String line : lines) {
                if (line.startsWith("Given")) {
                    TestCase testCase = new TestCase();
                    testCase.setGiven(line);
                    testCases.add(testCase);
                } else if (line.startsWith("When")) {
                    testCases.get(testCases.size() - 1).setWhen(line);
                } else if (line.startsWith("Then")) {
                    testCases.get(testCases.size() - 1).setThen(line);
                }
            }
            return testCases;
        } catch (Exception e) {
            return null;
        }
    }
    
    public String executeBDDTestCases(List<TestCase> testCases) {
        try {
            File featureFile = new File("./target/test.feature");
            try (FileWriter writer = new FileWriter(featureFile)) {
                writer.write("Feature: Automated Test Cases\n");
                for (TestCase testCase : testCases) {
                    writer.write("  Scenario: Execute BDD Test\n");
                    writer.write("    " + testCase.getGiven() + "\n");
                    writer.write("    " + testCase.getWhen() + "\n");
                    writer.write("    " + testCase.getThen() + "\n");
                }
            }

            // Execute using Cucumber
            String[] args = new String[]{"--glue", "com.example.testing", featureFile.getAbsolutePath()};
            io.cucumber.core.cli.Main.main(args);

            return "Execution completed. Check reports for details.";
        } catch (Exception e) {
            throw new RuntimeException("Test case execution failed", e);
        }
    }
    
}