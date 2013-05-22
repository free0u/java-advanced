/**
 * 
 */
package ru.ifmo.ctddev.evdokimov.task7;

/**
 * @author Anton Evdokimov
 *
 */
public class Main {
	/**
	 * Run args[0] client with one TaskRunner
	 * 
	 * @param args
	 * 	args[0] - count of clients
	 *  args[1] - count of threads in TaskRunner
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		if (args.length < 2) {
			System.out.println("Usage: Main <count of clients> <count of threads>");
			return;
		}
		int countOfClients = Integer.parseInt(args[0]);
		int countOfThreads = Integer.parseInt(args[1]);;
		
		TaskRunner tr = new TaskRunnerImpl(countOfThreads);
		
		Thread[] th = new Thread[countOfClients];
		
		for (int i = 0; i < countOfClients; i++) {
			Client c = new Client(tr);
			
			th[i] = new Thread(new ClientsExec(c, i));

			th[i].start();
		}
		
		for (int i = 0; i < countOfClients; i++) {
			th[i].join();
		}
	}
}
