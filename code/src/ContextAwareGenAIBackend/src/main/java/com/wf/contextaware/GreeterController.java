package com.wf.contextaware;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class GreeterController {

    @Value("${greeter.message}")
    private String greeterMessageFormat; 

    @GetMapping("/greet/{user}")
    public String greet(@PathVariable("user") String user) {
        String prefix = System.getenv().getOrDefault("GREETING_PREFIX", "Hi");
        System.out.println("prefix "+prefix);
        if (prefix == null) {
            prefix = "Hello!";
        }
        
        Map<String, String> ghd = new HashMap<>();
        ghd.put("a", "b");
        
        String x = ghd.get("a");

        return String.format(greeterMessageFormat, prefix, user);
    }
   
}