package ru.ifmo.ctddev.evdokimov.task7;

/**
 * 
 * Client for TaskRunner
 * 
 * @author Anton Evdokimov
 */
public class Client {
	/** store TaskRunner */
	private TaskRunner tr;
	
	/**
	 * Ctor generates TaskRunner
	 */
	public Client()  {
		this(new TaskRunnerImpl(1));
	}
	
	/**
	 * Ctor stores TaskTunner
	 * 
	 * @param tr TaskRunner item
	 */
	public Client(TaskRunner tr) {
		this.tr = tr;
	}
	
	/**
	 * call {@link ru.ifmo.ctddev.evdokimov.task7.Client#generateTasks()} with currentThreadId value
	 */
	public void generateTasks() {
		generateTasks((int)Thread.currentThread().getId());
	}

	/**
	 * 
	 * @param id - input value for task
	 */
	public void generateTasks(int id) {
		while (true) {
			Task<Integer, Integer> task = new TaskRefl<>();
			System.out.println(tr.run(task, id));
			if (Thread.interrupted()) {
				break;
			}
		}
	}
}
