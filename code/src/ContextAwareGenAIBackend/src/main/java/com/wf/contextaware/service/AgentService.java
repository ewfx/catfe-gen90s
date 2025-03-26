package com.wf.contextaware.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wf.contextaware.model.Message;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.http.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.web.client.RestTemplate;

@Service
public class AgentService {

	private String openaiApiKey = "ce9d1c14235b517f3cca3f445f916fc91f50256d265cbddc7628792bed2ce6cd";
	private String alphaVantageApiKey = "D5FI8OU0VIJ65Z2D";
	private String googleApiKey = "AIzaSyBB2ZPldM3JFWVDbUEgH6KkNAvLnU4o8rI";
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
		requestBody.put("functions", List.of(Map.of("name", "getStockPrice", "description",
				"Fetch the latest stock price for a given symbol", "parameters", Map.of("type", "object", "properties",
						Map.of("symbol", Map.of("type", "string")), "required", List.of("symbol")))));
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
		String url = "https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&symbol=" + symbol
				+ "&interval=5min&apikey=" + alphaVantageApiKey;
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

	// ✅ Generate BDD Test Cases using TogetherAI
	public String generateBDD(String context) {
		// try {
		// HttpHeaders headers = new HttpHeaders();
		// headers.set("Authorization", "Bearer " + openaiApiKey);
		// headers.set("Content-Type", "application/json");
		//
		// Map<String, String> requestBody = Map.of("context", context);
		// HttpEntity<Map<String, String>> request = new HttpEntity<>(requestBody,
		// headers);
		//
		// RestTemplate restTemplate = new RestTemplate();
		// ResponseEntity<String> response =
		// restTemplate.postForEntity("https://api.together.ai/generate", request,
		// String.class);
		//
		// if (response.getStatusCode().is2xxSuccessful()) {
		// saveGeneratedTestCases(response.getBody());
		// return "BDD Test Cases Generated Successfully and saved as
		// 'generated.feature'";
		// } else {
		// return "Failed to generate BDD Test Cases. Response: " + response.getBody();
		// }
		// } catch (Exception e) {
		// e.printStackTrace();
		// return "Error in BDD generation: " + e.getMessage();
		// }

		try {
			String apiUrl = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key="
					+ googleApiKey;

			HttpHeaders headers = new HttpHeaders();
			headers.set("Content-Type", "application/json");

			Map<String, Object> requestBody = Map.of("contents", List.of(Map.of("role", "user", "parts",
					List.of(Map.of("text", "Generate a BDD test case for: " + context)))));

			HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, request, String.class);

			if (response.getStatusCode().is2xxSuccessful()) {
				return response.getBody(); // ✅ Return the generated test case
			} else {
				return "Failed to generate BDD Test Cases. Response: " + response.getBody();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "Error in BDD generation: " + e.getMessage();
		}
	}

	// ✅ Save BDD Test Cases to a Feature File
	private void saveGeneratedTestCases(String testCases) throws IOException {
		File featureFile = new File("src/test/resources/generated.feature");
		featureFile.getParentFile().mkdirs(); // Ensure directories exist

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(featureFile))) {
			writer.write(testCases);
		}
	}

	// ✅ Execute BDD Tests using Cucumber
	public String executeTests() {
		// try {

		// ProcessBuilder processBuilder = new ProcessBuilder(
		// "C:\\Users\\kprag\\worksoftwares\\apache-maven-3.8.1-bin\\apache-maven-3.8.1\\bin\\mvn.cmd",
		// "test"
		// );
		// processBuilder.directory(new
		// File("C:\\Users\\kprag\\Downloads\\spring-boot-configmaps-demo-master\\spring-boot-configmaps-demo-master"));
		//
		//
		/*
		 * ProcessBuilder pb = new ProcessBuilder(
		 * "C:\\Users\\kprag\\worksoftwares\\apache-maven-3.8.1-bin\\apache-maven-3.8.1\\bin\\mvn",
		 * "test"); pb.directory(new File(System.getProperty("user.dir")));
		 */
		// processBuilder.redirectOutput(ProcessBuilder.Redirect.INHERIT);
		// processBuilder.redirectError(ProcessBuilder.Redirect.INHERIT);

		// Process process = processBuilder.start();
		// int exitCode = process.waitFor();
		//
		// return (exitCode == 0) ? "Tests executed successfully." : "Test execution
		// failed.";

		try {
			// Get the system's Maven command dynamically
			String mvnCommand = getMavenCommand();

			ProcessBuilder processBuilder = new ProcessBuilder(mvnCommand, "test");

			// Set the working directory dynamically (current project directory)
			processBuilder.directory(new File(System.getProperty("user.dir")));

			processBuilder.redirectOutput(ProcessBuilder.Redirect.INHERIT);
			processBuilder.redirectError(ProcessBuilder.Redirect.INHERIT);

			Process process = processBuilder.start();
			int exitCode = process.waitFor();

			return (exitCode == 0) ? "Tests executed successfully." : "Test execution failed.";
		} catch (Exception e) {
			return "Error executing tests: " + e.getMessage();
		}
	}

	private String getMavenCommand() {
		String os = System.getProperty("os.name").toLowerCase();

		if (os.contains("win")) {
			return "mvn.cmd"; // Windows
		} else {
			return "mvn"; // macOS/Linux
		}
	}

	// ✅ Generate Test Report
	public String generateReport() {
		try {
			// String reportPath = "target/cucumber-reports/report.html";
			String reportPath = new File("target/cucumber-reports/report.html").getAbsolutePath();

			File reportFile = new File(reportPath);

			if (reportFile.exists()) {
				return "Report generated successfully. Download it from /agent/download-report";
			} else {
				return "Report generation failed. Please check logs.";
			}
		} catch (Exception e) {
			return "Error generating report: " + e.getMessage();
		}
	}

}