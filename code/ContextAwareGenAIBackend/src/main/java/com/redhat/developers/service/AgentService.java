package com.redhat.developers.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.redhat.developers.model.Message;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AgentService {

    private String openaiApiKey = "878502523d92a65458d0269edce48e4df146b85545ee367e1081ea57f3f88e6b";
    private String alphaVantageApiKey = "D5FI8OU0VIJ65Z2D";

    private final CloseableHttpClient httpClient;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private List<Message> conversationContext = new ArrayList<>();
    private Map<String, String> financialData = new HashMap<>();

    @Autowired
    public AgentService(CloseableHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public String handleAgentRequest(String userInput) throws Exception {
        conversationContext.add(new Message("user", userInput));
        String response = callOpenAiWithFunctions();
        conversationContext.add(new Message("assistant", response));
        return response;
    }

    private String callOpenAiWithFunctions() throws Exception {
        HttpPost post = new HttpPost("https://api.together.xyz/v1/chat/completions");
        post.setHeader("Authorization", "Bearer " + openaiApiKey);
        post.setHeader("Content-Type", "application/json");

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "mistralai/Mixtral-8x22B-Instruct-v0.1");
        requestBody.put("messages", conversationContext);
        requestBody.put("functions", List.of(
            Map.of(
                "name", "getStockPrice",
                "description", "Fetch the latest stock price for a given symbol",
                "parameters", Map.of(
                    "type", "object",
                    "properties", Map.of("symbol", Map.of("type", "string")),
                    "required", List.of("symbol")
                )
            )
        ));
        requestBody.put("function_call", "auto");

        post.setEntity(new StringEntity(objectMapper.writeValueAsString(requestBody)));
        String responseJson = EntityUtils.toString(httpClient.execute(post).getEntity());
        System.out.println("Initial Response JSON: " + responseJson); // Debug
        JsonNode responseNode = objectMapper.readTree(responseJson);

        JsonNode messageNode = responseNode.path("choices").get(0).path("message");
        if (messageNode.has("function_call")) {
            String functionName = messageNode.path("function_call").path("name").asText();
            String args = messageNode.path("function_call").path("arguments").asText();
            JsonNode argsNode = objectMapper.readTree(args);

            if ("getStockPrice".equals(functionName)) {
                String symbol = argsNode.path("symbol").asText();
                String price;
                try {
                    price = fetchStockPrice(symbol);
                    financialData.put(symbol, price);
                    String response = symbol + " is at $" + price + " as of now.";
                    conversationContext.add(new Message("function", "Stock price of " + symbol + " is $" + price));
                    return response;
                } catch (Exception e) {
                    String errorResponse = "Error fetching price for " + symbol + ": " + e.getMessage();
                    conversationContext.add(new Message("function", errorResponse));
                    return errorResponse;
                }
            }
            return "I don’t know how to handle that function.";
        }
        return messageNode.has("content") ? messageNode.path("content").asText() : "No response content available.";
    }

    private String fetchStockPrice(String symbol) throws Exception {
        String url = "https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&symbol=" + symbol +
                     "&interval=5min&apikey=" + alphaVantageApiKey;
        HttpGet get = new HttpGet(url);
        String responseJson = EntityUtils.toString(httpClient.execute(get).getEntity());
        JsonNode data = objectMapper.readTree(responseJson);

        if (data.has("Error Message")) {
            throw new RuntimeException("Invalid symbol: " + symbol);
        }

        JsonNode timeSeries = data.path("Time Series (5min)");
        if (!timeSeries.fieldNames().hasNext()) {
            throw new RuntimeException("No data available for " + symbol);
        }
        String latestTime = timeSeries.fieldNames().next();
        return timeSeries.path(latestTime).path("4. close").asText();
    }

    public void resetContext() {
        conversationContext.clear();
        financialData.clear();
    }

    public Map<String, String> getFinancialData() {
        return financialData;
    }
}