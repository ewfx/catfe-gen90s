package com.wf.contextaware.configuration;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class KycResult {
    @Id
    private String requestId;
    private String verificationStatus; // e.g., "Verified", "Rejected"
    private double riskScore;          // 0.0 to 1.0
    private String analysisDetails;    // Detailed explanation from model
    
    
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	public String getVerificationStatus() {
		return verificationStatus;
	}
	public void setVerificationStatus(String verificationStatus) {
		this.verificationStatus = verificationStatus;
	}
	public double getRiskScore() {
		return riskScore;
	}
	public void setRiskScore(double riskScore) {
		this.riskScore = riskScore;
	}
	public String getAnalysisDetails() {
		return analysisDetails;
	}
	public void setAnalysisDetails(String analysisDetails) {
		this.analysisDetails = analysisDetails;
	}
    
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}
    
    
}