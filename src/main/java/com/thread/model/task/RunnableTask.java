package com.thread.model.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class RunnableTask implements Runnable {
	
	private Logger LOG = LoggerFactory.getLogger(RunnableTask.class);
	
	private String name;
	private Exception e;
	private Object response;
	private Object lock;
	private boolean timeout = false;
	private volatile boolean completed = false;
	
	public RunnableTask(String name) {
		this.name = name;
	}
	
	public abstract Object doRun();

	@Override
	public void run() {
		
		LOG.debug(" Starting the Thread : " + this.name);
		
		try {
			
			this.response = this.doRun();
			
		}catch(Exception e) {
			
			LOG.error("Exceptoion in current running thread : "+ this.name);
			
			this.e = e;
		}
		
		
		this.completed = true; // set true when thread has no exception
		
		synchronized (this.lock) {
			
			this.lock.notifyAll(); // notifying to all the threads
			
		}
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Exception getE() {
		return e;
	}

	public void setE(Exception e) {
		this.e = e;
	}

	public Object getResponse() {
		return response;
	}

	public void setResponse(Object response) {
		this.response = response;
	}

	public Object getLock() {
		return lock;
	}

	public void setLock(Object lock) {
		this.lock = lock;
	}

	public boolean isTimeout() {
		return timeout;
	}

	public void setTimeout(boolean timeout) {
		this.timeout = timeout;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}
	
	

}
