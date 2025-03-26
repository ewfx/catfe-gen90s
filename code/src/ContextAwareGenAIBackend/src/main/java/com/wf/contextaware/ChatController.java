package com.wf.contextaware;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.wf.contextaware.model.ChatRequest;
import com.wf.contextaware.service.AgentService;

import org.springframework.core.io.Resource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;

@RestController
@RequestMapping("/chat")
@CrossOrigin(origins = "http://localhost:3000")
public class ChatController {

	@Autowired
	private AgentService agentService;

	@PostMapping
	public ResponseEntity<String> chat(@RequestBody ChatRequest request) {
		try {
			String response = agentService.handleAgentRequest(request.getMessage());
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return ResponseEntity.status(500).body("Something went wrong!");
		}
	}

	// ✅ Generate BDD Test Cases
	@PostMapping("/generate")
	public ResponseEntity<Map<String, String>> generateBDD(@RequestBody Map<String, String> request) {
		String context = request.get("context");
		String response = agentService.generateBDD(context);
		return ResponseEntity.ok(Map.of("message", response));
	}

	// ✅ Execute BDD Tests
	@PostMapping("/execute")
	public ResponseEntity<Map<String, String>> executeTests() {
		String response = agentService.executeTests();
		return ResponseEntity.ok(Map.of("message", response));
	}

	// ✅ Generate Report
	@PostMapping("/report")
	public ResponseEntity<Map<String, String>> generateReport() {
		String response = agentService.generateReport();
		String downloadLink = "http://localhost:8083/chat/download-report";
		return ResponseEntity.ok(Map.of("message", response, "downloadLink", downloadLink));
	}

	// ✅ Download Report
	@GetMapping("/download-report")
	public ResponseEntity<Resource> downloadReport() throws IOException {
		String reportPath = "C:/Users/kprag/Downloads/spring-boot-configmaps-demo-master/spring-boot-configmaps-demo-master/target/cucumber-reports/report.html"; // Ensure
																																									// the
																																									// path
																																									// is
																																									// correct
		File reportFile = new File(reportPath);

		if (!reportFile.exists()) {
			return ResponseEntity.notFound().build();
		}

		InputStreamResource resource = new InputStreamResource(new FileInputStream(reportFile));
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=report.html")
				.contentType(MediaType.TEXT_HTML).body(resource);
	}

}
