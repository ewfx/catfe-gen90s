package com.wellsfargo.developers.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class Message {
    private String role;
    private String content;
	public Message(String role, String content) {
		this.role = role;
		this.content = content;
		// TODO Auto-generated constructor stub
	}
	
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
    
}
