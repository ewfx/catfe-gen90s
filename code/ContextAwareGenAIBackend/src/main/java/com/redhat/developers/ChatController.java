package com.redhat.developers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.redhat.developers.model.ChatRequest;
import com.redhat.developers.service.AgentService;

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
}
