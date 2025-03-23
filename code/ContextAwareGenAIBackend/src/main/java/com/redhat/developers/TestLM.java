package com.redhat.developers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TestLM {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String x = "3456543";
		
		TestLM tlm = new TestLM();
		String converedString = tlm.convertedString(x);
		System.out.println("converted string is "+converedString+ " and main string is "+x);
		boolean isPalindromeStr = false;
		if(x.equalsIgnoreCase(converedString)) {
			isPalindromeStr = true;
		}
		
		
		if(isPalindromeStr) {
			System.out.println("Its a palindrome");
		}
	}
	
	private String convertedString(String x) {
		
		String y = x;
		for(int i=0; i<x.length();i++) {
			for(int j=0;j<x.length();j--) {
				int temp = 0;
				if(temp<=j) {
				y.replace(x.charAt(i),y.charAt(j));
				} else {
					break;
				}
			}
		}
		
		System.out.println(y);
		
		return y;
	}
	
	
}
