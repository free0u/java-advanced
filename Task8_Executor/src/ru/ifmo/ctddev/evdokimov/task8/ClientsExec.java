package ru.ifmo.ctddev.evdokimov.task8;

/**
 * Class for multithread execution of clients
 *
 * @author Anton Evdokimov
 */
public class ClientsExec implements Runnable {
	/** Stored id for task */
	private int id;
	/** Stored task */
	private Client cl;
	
	/**
	 * @param cl - input client
	 * @param id - id for client
	 */
	public ClientsExec(Client cl, int id) {
		this.id = id;
		this.cl = cl;
	}
	@Override
	public void run() {
		cl.generateTasks(id);
	}
}