package com.thread.model.task;

import com.thread.service.ThreadService;

public class Method3Task extends RunnableTask {
	
	private ThreadService service;

	public Method3Task(String name, ThreadService service) {
		super(name);
		this.service = service;
	}

	@Override
	public Object doRun() {
		return service.method3();
	}

}
