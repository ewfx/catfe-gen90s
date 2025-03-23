package com.redhat.developers.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.redhat.developers.service.EthicalHackService;
import com.redhat.developers.service.GenAIService;
import com.redhat.developers.service.OpenAIService;
import com.redhat.developers.service.TestExecutorService;



@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class ContextAwareController {

    @Autowired
    private OpenAIService openAIService;
    
    @Autowired
    private EthicalHackService ethicalHackService;
    
    @Autowired
    private GenAIService genAIService;
    
    @Autowired
    private TestExecutorService testExecutorService;

    @PostMapping("/generate-test-cases")
    public Map<String, String> generateTestCases(@RequestBody Map<String, String> requestBody) {
    	System.out.println("hi");
    	System.out.println("description"+requestBody.get("description"));
        return openAIService.generateTestCases(requestBody.get("description"));
    }
    
	/*
	 * @PostMapping("/ethical_hack") public ResponseEntity<Map<String, String>>
	 * ethicalHack(@RequestParam String url) { String reportUrl =
	 * ethicalHackService.simulateEthicalHack(url); Map<String, String> response =
	 * new HashMap<>(); response.put("report_url", reportUrl); return
	 * ResponseEntity.ok(response); }
	 */
    
//    @PostMapping(value = "/ethical_hack", produces = MediaType.TEXT_HTML_VALUE)
//    @Operation(summary = "Simulate ethical hack on app", description = "Performs an ethical hack using OWASP ZAP and returns a downloadable HTML report.")
//    public ResponseEntity<byte[]> ethicalHack(
//            @Parameter(description = "URL of the app to hack") @RequestParam String url) {
//        String reportContent = ethicalHackService.generateEthicalHackReport(url);
//
//        // Set headers for file download
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.TEXT_HTML);
//        headers.setContentDispositionFormData("attachment", "ethical_hack_report.html");
//        headers.setContentLength(reportContent.getBytes().length);
//
//        return new ResponseEntity<>(reportContent.getBytes(), headers, org.springframework.http.HttpStatus.OK);
//    }
    
  
    @PostMapping(value = "/ethical_hack", produces = MediaType.TEXT_HTML_VALUE)
    @Operation(summary = "Simulate ethical hack on app", description = "Performs an ethical hack using OWASP ZAP and returns a downloadable HTML report.")
    public ResponseEntity<byte[]> ethicalHack(@RequestBody Map<String, String> requestBody) {
        String url = requestBody.get("url"); // Extract URL from JSON body

        if (url == null || url.isBlank()) {
            return ResponseEntity.badRequest().body("URL parameter is missing".getBytes());
        }

        String reportContent = ethicalHackService.generateEthicalHackReport(url);

        // Set headers for file download
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_HTML);
        headers.setContentDispositionFormData("attachment", "ethical_hack_report.html");
        headers.setContentLength(reportContent.getBytes().length);

        return new ResponseEntity<>(reportContent.getBytes(), headers, HttpStatus.OK);
    }

    @PostMapping("/analyze_payment_fraud_data")
    @Operation(summary = "Analyze payment fraud data and generate tests", description = "Uploads transaction data, detects fraud, and generates BDD test cases.")
    public ResponseEntity<Map<String, String>> analyzePaymentFraudData(
            @Parameter(description = "CSV file containing bank transaction data") @RequestParam("file") MultipartFile file) {
        try {
            String fraudAnalysisAndTests = genAIService.analyzePaymentFraudDataAndGenerateTests(file.getInputStream());
            String executionSummary = testExecutorService.executeBddTests(fraudAnalysisAndTests);
            Map<String, String> response = new HashMap<>();
            response.put("fraud_analysis_and_tests", fraudAnalysisAndTests);
            response.put("execution_summary", executionSummary);
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Failed to process file: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    
    @PostMapping("/generate_bdd")
    @Operation(summary = "Generate BDD test cases from app description", description = "Generates Gherkin-style BDD test cases based on the provided app description.")
    public ResponseEntity<Map<String, String>> generateBdd(
            @Parameter(description = "Description of the financial app") @RequestParam String description) {
        String bddTests = genAIService.generateBddTests(description);
        Map<String, String> response = new HashMap<>();
        response.put("bdd_tests", bddTests);
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/execute_tests")
    @Operation(summary = "Execute BDD tests", description = "Executes the generated BDD tests and returns a report URL.")
    public ResponseEntity<Map<String, String>> executeTests() {
        String reportUrl = testExecutorService.executeBddTests("reports/bdd_tests.feature");
        Map<String, String> response = new HashMap<>();
        response.put("report_url", reportUrl);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/generate_bdd_from_url")
    @Operation(summary = "Generate BDD test cases from URL", description = "Generates BDD test cases by analyzing the provided app URL.")
    public ResponseEntity<Map<String, String>> generateBddFromUrl(
            @Parameter(description = "URL of the financial app") @RequestParam String url) {
        String bddTests = genAIService.generateBddTests("App at " + url + " with login and payment features.");
        Map<String, String> response = new HashMap<>();
        response.put("bdd_tests", bddTests);
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/generate_payment_tests")
    @Operation(summary = "Generate payment transaction tests", description = "Generates BDD test cases for payment transactions based on the provided description.")
    public ResponseEntity<Map<String, String>> generatePaymentTests(
            @Parameter(description = "Description of the payment transaction flow") @RequestParam String description) {
        String paymentTests = genAIService.generatePaymentTransactionTests(description);
        Map<String, String> response = new HashMap<>();
        response.put("payment_tests", paymentTests);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/generate_fraud_tests")
    @Operation(summary = "Generate fraud detection tests", description = "Generates BDD test cases for fraud detection in a banking app based on the provided description.")
    public ResponseEntity<Map<String, String>> generateFraudTests(
            @Parameter(description = "Description of the fraud detection scenario") @RequestParam String description) {
        String fraudTests = genAIService.generateFraudDetectionTests(description);
        Map<String, String> response = new HashMap<>();
        response.put("fraud_tests", fraudTests);
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/chatbot")
    @Operation(summary = "Interact with chatbot", description = "Sends a message to the chatbot and receives a response.")
    public ResponseEntity<Map<String, String>> chatbot(
            @Parameter(description = "Message to send to the chatbot") @RequestParam String message) {
        String response = genAIService.handleChatbotMessage(message);
        Map<String, String> resp = new HashMap<>();
        resp.put("response", response);
        return ResponseEntity.ok(resp);
    }
}
