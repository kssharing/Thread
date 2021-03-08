package com.thread;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thread.model.task.Method1Task;
import com.thread.model.task.Method2Task;
import com.thread.model.task.Method3Task;
import com.thread.model.task.Method4Task;
import com.thread.model.task.TaskHandler;
import com.thread.service.ThreadService;

@RestController
public class ThreadController {
	
	@Autowired
	private ThreadService service;
	
	
   @GetMapping(path = "/call", produces = "application/json")
   public String retrieveCustomer(){
   	
	   Method1Task task1 = new Method1Task("task1", service);
	   Method2Task task2 = new Method2Task("task2", service);
	   Method3Task task3 = new Method3Task("task3", service);
	   Method4Task task4 = new Method4Task("task4", service);
	   
	   TaskHandler handler = new TaskHandler(3000, task1, task2, task3, task4);
	   handler.start();
	   
	   
	   if(handler.isException() || handler.isTimedOut()) {
		   
		   if(task1.getE() != null || task1.isTimeout()) {
			   
		   }
		   
	   }
	   
	   
   	
   	
   	return "SUCCESS";
   }
}
