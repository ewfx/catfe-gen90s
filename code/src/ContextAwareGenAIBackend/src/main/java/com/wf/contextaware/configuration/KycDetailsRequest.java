package com.wf.contextaware.configuration;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;


@Data
@Entity
public class KycDetailsRequest {
	
	@Id
    private String requestId;
    private String customerName;
    private String idNumber;
    private String uploadedImagePath; // Path to ID image
    private String financialData;
    
    
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getIdNumber() {
		return idNumber;
	}
	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	public String getUploadedImagePath() {
		return uploadedImagePath;
	}
	public void setUploadedImagePath(String uploadedImagePath) {
		this.uploadedImagePath = uploadedImagePath;
	}
	public String getFinancialData() {
		return financialData;
	}
	public void setFinancialData(String financialData) {
		this.financialData = financialData;
	}
    
    

}
