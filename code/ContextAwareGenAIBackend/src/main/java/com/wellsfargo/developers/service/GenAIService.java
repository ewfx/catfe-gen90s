package com.wellsfargo.developers.service;

import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class GenAIService {

    // Replace with your actual API key and endpoint
    private static final String API_KEY = "878502523d92a65458d0269edce48e4df146b85545ee367e1081ea57f3f88e6b";
    private static final String API_ENDPOINT = "https://api.together.xyz/v1/fraud-detection";
    private static final OkHttpClient client = new OkHttpClient();

    // Rule-based fraud detection thresholds
    private static final double HIGH_AMOUNT_THRESHOLD = 1000.0;
    private static final double BALANCE_THRESHOLD = 2000.0;
    private static final double BALANCE_RATIO_THRESHOLD = 0.5;
    private static final int LOGIN_ATTEMPTS_THRESHOLD = 2;
    private static final int DURATION_THRESHOLD = 200;

    public String analyzePaymentFraudDataAndGenerateTests(InputStream fileInputStream) {
        List<String[]> transactions = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(fileInputStream))) {
            String line;
            boolean isHeader = true;
            while ((line = br.readLine()) != null) {
                if (isHeader) {
                    isHeader = false; // Skip header
                    continue;
                }
                transactions.add(line.split(","));
            }
        } catch (IOException e) {
            return "Error reading file: " + e.getMessage();
        }

        StringBuilder fraudAnalysis = new StringBuilder("Fraudulent Transactions Detected:\n");
        StringBuilder testCases = new StringBuilder("Feature: Payment Fraud Detection Testing\n");

        for (String[] transaction : transactions) {
            try {
                String transactionId = transaction[0];
                double amount = Double.parseDouble(transaction[2]);
                String location = transaction[5];
                int loginAttempts = Integer.parseInt(transaction[13]);
                double accountBalance = Double.parseDouble(transaction[14]);

                // Rule-based fraud detection
                boolean isFraudulent = false;
                StringBuilder fraudReason = new StringBuilder();

                if (amount > HIGH_AMOUNT_THRESHOLD) {
                    isFraudulent = true;
                    fraudReason.append("High transaction amount ($").append(amount).append(" > $").append(HIGH_AMOUNT_THRESHOLD).append(")");
                }
                if (amount > BALANCE_RATIO_THRESHOLD * accountBalance && accountBalance < BALANCE_THRESHOLD) {
                    isFraudulent = true;
                    if (fraudReason.length() > 0) fraudReason.append(" and ");
                    fraudReason.append("Low account balance ($").append(accountBalance).append(" < $").append(BALANCE_THRESHOLD)
                            .append(", amount > ").append(BALANCE_RATIO_THRESHOLD * 100).append("% of balance)");
                }
                if (loginAttempts > LOGIN_ATTEMPTS_THRESHOLD) {
                    isFraudulent = true;
                    if (fraudReason.length() > 0) fraudReason.append(" and ");
                    fraudReason.append("Multiple login attempts (").append(loginAttempts).append(" > ").append(LOGIN_ATTEMPTS_THRESHOLD).append(")");
                }
                int duration = Integer.parseInt(transaction[12]);
                if (duration > DURATION_THRESHOLD) {
                    isFraudulent = true;
                    if (fraudReason.length() > 0) fraudReason.append(" and ");
                    fraudReason.append("Unusual transaction duration (").append(duration).append("s > ").append(DURATION_THRESHOLD).append("s)");
                }

                // Optional GenAI API call for additional validation
                boolean genAiFraud = callGenAIFraudDetection(transaction);
                if (genAiFraud) {
                    isFraudulent = true;
                    if (fraudReason.length() > 0) fraudReason.append(" and ");
                    fraudReason.append("Flagged by GenAI model");
                }

                if (isFraudulent) {
                    fraudAnalysis.append(String.format("Transaction ID: %s, Amount: $%.2f, Account Balance: $%.2f, Location: %s, Login Attempts: %d\n",
                            transactionId, amount, accountBalance, location, loginAttempts));
                    fraudAnalysis.append("  Reason: ").append(fraudReason.toString()).append("\n");

                    testCases.append(String.format("Scenario: Detect Fraudulent Transaction - ID %s\n" +
                            "  Given a transaction with ID %s and amount $%.2f from %s with account balance $%.2f and %d login attempts\n" +
                            "  When the transaction is processed\n" +
                            "  Then the fraud detection system flags it as suspicious due to %s\n",
                            transactionId, transactionId, amount, location, accountBalance, loginAttempts, fraudReason.toString()));
         }
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                fraudAnalysis.append("Error processing transaction ").append(transaction[0]).append(": ").append(e.getMessage()).append("\n");
            }
        }

        return fraudAnalysis.toString() + "\n" + testCases.toString();
    }

    // Placeholder for GenAI API call
    private boolean callGenAIFraudDetection(String[] transaction) {
        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("transaction_id", transaction[0]);
            requestBody.put("amount", Double.parseDouble(transaction[2]));
            requestBody.put("location", transaction[5]);
            requestBody.put("login_attempts", Integer.parseInt(transaction[13]));
            requestBody.put("account_balance", Double.parseDouble(transaction[14]));
            requestBody.put("duration", Integer.parseInt(transaction[12]));

            RequestBody body = RequestBody.create(
                    requestBody.toString(),
                    MediaType.parse("application/json")
            );

            Request request = new Request.Builder()
                    .url(API_ENDPOINT)
                    .addHeader("Authorization", "Bearer " + API_KEY)
                    .post(body)
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) return false;

                String responseBody = response.body().string();
                JSONObject jsonResponse = new JSONObject(responseBody);
                return jsonResponse.getBoolean("is_fraudulent"); // Adjust based on actual API response
            }
        } catch (Exception e) {
            System.err.println("GenAI API call failed: " + e.getMessage());
            return false; // Fallback to rule-based detection if API fails
        }
    }

    // Existing methods (unchanged for brevity, add if needed)
    public String generateBddTests(String description) {
    	return String.format("Feature: Financial App Testing\n" +
    	        "  Scenario: Basic Functionality\n" +
    	        "    Given the app is running\n" +
    	        "    When the user interacts with %s\n" +
    	        "    Then the app responds successfully\n",
    	        description);

    }

    public String handleChatbotMessage(String message) {
        if (message.toLowerCase().contains("generate bdd")) {
            return "Generated BDD:\n" + generateBddTests(message);
        } else if (message.toLowerCase().contains("test url")) {
            String url = message.split("test url")[1].trim();
            return "Generated BDD for " + url + ":\n" + generateBddTests("App at " + url);
        } else if (message.toLowerCase().contains("hack")) {
            String url = message.split("hack")[1].trim();
            return "Running ethical hack on " + url + ". Check the report at /reports/hack_report.html.";
        } else if (message.toLowerCase().contains("payment")) {
            String desc = message.split("payment")[1].trim();
            return "Generated Payment Tests:\n" + generatePaymentTransactionTests(desc);
        } else if (message.toLowerCase().contains("fraud")) {
            String desc = message.split("fraud")[1].trim();
            return "Generated Fraud Detection Tests:\n" + generateFraudDetectionTests(desc);
        }
        return "I can generate BDD tests, test URLs, simulate hacks, payment tests, or fraud tests. What do you want?";
    }
    
    public String generatePaymentTransactionTests(String description) {
    	return String.format("Feature: Payment Transactions Testing\n" +
    	        "  Scenario: Successful Payment Processing\n" +
    	        "    Given the user is logged into the banking app\n" +
    	        "    When the user initiates a payment with %s\n" +
    	        "    Then the payment is processed successfully\n" +
    	        "  Scenario: Insufficient Funds\n" +
    	        "    Given the user has insufficient funds\n" +
    	        "    When the user attempts a payment with %s\n" +
    	        "    Then the payment is rejected with an error\n",
    	        description, description);


    }

    public String generateFraudDetectionTests(String description) {
    	return String.format("Feature: Fraud Detection Testing\n" +
    	        "  Scenario: Detect Suspicious Transaction\n" +
    	        "    Given a transaction with %s is initiated\n" +
    	        "    When the transaction exceeds normal limits\n" +
    	        "    Then the fraud detection system flags it\n" +
    	        "  Scenario: Normal Transaction\n" +
    	        "    Given a transaction with %s is initiated\n" +
    	        "    When the transaction is within normal limits\n" +
    	        "    Then the fraud detection system approves it\n",
    	        description, description);

    }
    
    
    
    
}