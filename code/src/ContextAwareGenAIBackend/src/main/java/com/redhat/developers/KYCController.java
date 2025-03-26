package com.redhat.developers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.redhat.developers.configuration.KycDetailsRequest;
import com.redhat.developers.configuration.KycResult;
import com.redhat.developers.service.KycHugService;




@RestController
@RequestMapping("/api/kyc")
@CrossOrigin(origins = "http://localhost:3000")
public class KYCController {
	
	@Autowired private KycHugService kycService;

    @PostMapping("/verify")
    public ResponseEntity<KycResult> verifyKyc(@RequestBody KycDetailsRequest request) {
        KycResult result = kycService.processKyc(request);
        return ResponseEntity.ok(result);
    }	
	
}
