package com.thread.model.task;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TaskHandler {
	
	private Logger LOG = LoggerFactory.getLogger(RunnableTask.class);
	private ExecutorService executor = Executors.newCachedThreadPool(); // to execute the task
	
	private long timeOut;
	private boolean timedOut = false;
	private RunnableTask[] tasks;
	private List<RunnableTask> uncompleted;
	private Object lock;
	
	public TaskHandler(long timeOut, RunnableTask...runnableTasks) {
		
		this.timeOut = timeOut;
		this.tasks = runnableTasks;
		this.lock = this;
		this.uncompleted = new ArrayList<RunnableTask>();
		
	}
	
	
	public void start() {
		
		
		long waitTime = this.timeOut; // total time
		long startTime; // task start time
		long processTime; // task processing time
		
		synchronized (this.lock) {
			
			for(int i = 0; i < this.tasks.length; i++) {
				
				this.tasks[i].setLock(this); // lock the first object for processing
				
				this.uncompleted.add(tasks[i]); // add all the tasks to uncompleted list to check the count of completed and uncompleted tasks
				
				LOG.debug("Executing task : "+ tasks[i].getName());
				
				this.executor.execute(tasks[i]);
				
			}
			
			try {
				
				do {
				
					startTime = System.currentTimeMillis();
					
					this.wait(waitTime); // wait until specified timeout
					
					
					processTime = System.currentTimeMillis() - startTime; // calculate the processing time
					
					waitTime = waitTime - processTime; // Subtract the processing time with total timeout
					
					
					List<RunnableTask> tempTasks = new ArrayList<RunnableTask>(); // to collect all the completed tasks
					
					for(RunnableTask task : tasks) {
						
						if(task.isCompleted()) {
							tempTasks.add(task);
						}
					}
					
					this.uncompleted.removeAll(tempTasks); // remove completed tasks and keep the uncompleted tasks to get the exception and timeout
					
					
				} while(this.uncompleted.size() > 0 && waitTime > 0);
				
				
			}catch(Exception e) {
				e.printStackTrace();
				
			}
			
			//set task handler timeout
			if(waitTime <= 0 && this.uncompleted.size() >0) {
				this.timedOut = true;
			}
			
			
			// set timeout at task level
			for(RunnableTask task : this.tasks) {
				if(this.uncompleted.contains(task)) {
					task.setTimeout(true);
				} else {
					task.setTimeout(false);
				}
			}
			
			notifyAll();
			
		}
	}
	
	public boolean isException() { // to check whether task has exception
		
		for(RunnableTask task : this.tasks) {
			if(task.getE() != null) {
				return true;
			}
		}
		return false;
		
	}


	public boolean isTimedOut() {
		return timedOut;
	}

	
}
