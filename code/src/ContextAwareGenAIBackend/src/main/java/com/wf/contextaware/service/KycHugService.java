package com.wf.contextaware.service;


import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wf.contextaware.configuration.KycDetailsRequest;
import com.wf.contextaware.configuration.KycResult;
import com.wf.contextaware.configuration.KycResultRepository;


@Service
public class KycHugService {
	

    @Autowired private TogetherAiService aiService;
    @Autowired private KycResultRepository resultRepo;

    public KycResult processKyc(KycDetailsRequest request)  {
        // Extract text from ID image
        //String idText = ocrService.extractTextFromImage(request.getUploadedImagePath());
    	//String idText = "12345";
    	String idText = "DrivingLicence.jpeg";

        // Create context-aware prompt
    	String prompt = String.format(
    		    "Analyze the following KYC data for loan processing:\n" +
    		    "- Customer Name: %s\n" +
    		    "- ID Number: %s\n" +
    		    "- ID Text: %s\n" +
    		    "- Financial Data: %s\n" +
    		    "Determine if the customer is verified and assign a risk score (0.0-1.0).\n" +
    		    "Provide detailed analysis explaining your decision.",
    		    request.getCustomerName(), request.getIdNumber(), idText, request.getFinancialData()
    		);


        
        try {

        // Call Together AI
        String rawResult = aiService.analyzeKycContext(prompt);
            
        // Parse JSON response (example assumes structured output)
        JSONObject jsonResult = new JSONObject(rawResult);        
        JSONArray choices = jsonResult.getJSONArray("choices");
        JSONObject firstChoice = choices.getJSONObject(0);
        JSONObject message = firstChoice.getJSONObject("message");
        String content = message.getString("content");
        
        String verificationStatus = extractValue(content, "Verification Status: (.*?)$");
        String riskScore1 = extractValue(content, "risk_score = (\\d+\\.\\d+)");
        
        System.out.println("verificationStatus :: "+verificationStatus);
        System.out.println("riskScore1 :: "+riskScore1);

        // Extract verification and risk score (simplified parsing)
        String status = content.contains("verified") ? "Verified" : "Rejected";
        double riskScore = extractRiskScore(content); // Custom logic to parse score
        
     // Extract key values from the Python script in content
   /*     String customerName = formatText(content, "customer_name = \"(.*?)\"");
        String idNumber = formatText(content, "id_number = \"(.*?)\"");
        String idText1 = formatText(content, "id_text = \"(.*?)\"");
        String income = formatText(content, "income = financial_data\\[\"income\"\\].*?(\\d+)");
        String creditScore = formatText(content, "credit_score = financial_data\\[\"creditScore\"\\].*?(\\d+)");
        String defaults = formatText(content, "defaults = financial_data\\[\"defaults\"\\].*?(\\d+)");
        String loanHistory = formatText(content, "loan_history = financial_data\\[\"loanHistory\"\\].*?\"(.*?)\"");
        String verificationStatus1 = formatText(content, "verification_status = \"(.*?)\"");
        String riskScore = formatText(content, "risk_score = (\\d+\\.\\d+)");
        */

        // Display in an understandable format
        /*
        System.out.println("=== KYC Analysis Result ===");
        System.out.println("Customer Name: " + customerName);
        System.out.println("ID Number: " + idNumber);
        System.out.println("ID Text: " + idText1);
        System.out.println("Financial Data:");
        System.out.println("  - Income: $" + income);
        System.out.println("  - Credit Score: " + creditScore);
        System.out.println("  - Defaults: " + defaults);
        System.out.println("  - Loan History: " + loanHistory);
        System.out.println("Verification Status: " + verificationStatus1);
        System.out.println("Risk Score: " + riskScore1);
        System.out.println("===========================");
        
        */
        
        
      /*  String formattedResult = String.format(
                "=== KYC Analysis Result ===\n" +
                "Customer Name: %s\n" +
                "ID Number: %s\n" +
                "ID Text: %s\n" +
                "Financial Data:\n" +
                "  - Income: $%s\n" +
                "  - Credit Score: %s\n" +
                "  - Defaults: %s\n" +
                "  - Loan History: %s\n" +
                "Verification Status: %s\n" +
                "Risk Score: %s\n" +
                "===========================",
                customerName, idNumber, idText1, income, creditScore, defaults, loanHistory, verificationStatus1, riskScore1
            );
            */

        // Save and return result
        KycResult result = new KycResult();
        result.setRequestId(request.getRequestId());
        result.setVerificationStatus(status);
        result.setRiskScore(riskScore);
        result.setAnalysisDetails(content);
        
        //System.out.println(formattedResult);
        
        //return resultRepo.save(result);
        return result;
        }catch(Exception e) {
        	
        }
        
        return null;
    }
    
    private static String extractValue(String content, String pattern) {
        //java.util.regex.Pattern p = java.util.regex.Pattern.compile(pattern);
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(pattern, java.util.regex.Pattern.MULTILINE);
        java.util.regex.Matcher m = p.matcher(content);
        return m.find() ? m.group(1) : "Not found";
    }

    private double extractRiskScore(String analysis) {
        // Example: Extract number between 0.0 and 1.0 from text
        Pattern pattern = Pattern.compile("risk score: ([0-1]\\.\\d+)");
        Matcher matcher = pattern.matcher(analysis);
        return matcher.find() ? Double.parseDouble(matcher.group(1)) : 0.5;
    }
    
    
    private static String formatText(String content, String pattern) {
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(pattern, java.util.regex.Pattern.DOTALL);
        java.util.regex.Matcher m = p.matcher(content);
        return m.find() ? m.group(1) : "Not found";
    }
}
