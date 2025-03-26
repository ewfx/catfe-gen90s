package com.redhat.developers.service;



import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

@Service
public class TogetherAiService {
    private final RestTemplate restTemplate;
    //private final String apiKey = "your-together-ai-api-key";
    //private final String apiUrl = "https://api.together.ai/v1/inference";
    
	private static final String apiUrl = "https://api.together.xyz/v1/chat/completions";
    private static final String apiKey = "878502523d92a65458d0269edce48e4df146b85545ee367e1081ea57f3f88e6b"; // Replace with your API key

    public TogetherAiService() {
        this.restTemplate = new RestTemplate();
    }

    public String analyzeKycContext(String prompt) {
        /*String requestBody = """
            {
                "model": "HuggingFaceH4/zephyr-7b-beta",
                "prompt": "%s",
                "max_tokens": 512,
                "temperature": 0.7
            }
            """.formatted(prompt); 
            */
    	
    	JSONObject json = new JSONObject();
       
        try {
			json.put("prompt", prompt);
			 json.put("model", "meta-llama/Llama-3.3-70B-Instruct-Turbo"); //meta-llama/Llama-3.3-70B-Instruct-Turbo
			 json.put("max_tokens", 512);
		        json.put("temperature", 0.7);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // Automatically escapes special characters
       
        
        String requestBody = json.toString();

     // Set up headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + apiKey);

        // Create HTTP entity with body and headers
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        // Make POST request and get response
        ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, entity, String.class);
        return response.getBody(); // Return the raw JSON response as a String
    }
}