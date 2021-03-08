package com.thread.model.task;

import org.springframework.beans.factory.annotation.Autowired;

import com.thread.service.ThreadService;

public class Method4Task extends RunnableTask {
	
	@Autowired
	private ThreadService service;

	public Method4Task(String name, ThreadService service) {
		super(name);
		this.service = service;
	}

	@Override
	public Object doRun() {
		return service.method4();
	}

}
