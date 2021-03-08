package com.thread.service;

import org.springframework.stereotype.Service;

@Service
public class ThreadService {
	
	public String method1() {
		
		System.out.println("method 1 call");
		
		return "method1";
	}
	
	public String method2() {
		
		System.out.println("method 2 call");
		
		throw new RuntimeException();
	}


	public String method3() {
		
		System.out.println("method 3 call");
	
		return "method3";
	}


	public String method4() {
		
		System.out.println("method 4 call");
		
		for(int i = 1; i <=100; i++) {
			// infinite loop for check the timeout
		}
		
		return "method4";
	}
	

}
