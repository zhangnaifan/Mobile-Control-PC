package manager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadManager {
	private volatile static ThreadManager instance;
	private ExecutorService executorService = Executors.newFixedThreadPool(5);
	private ThreadManager(){}
	public static ThreadManager getInstance() {
		if(instance==null){
			synchronized (ThreadManager.class) {
				instance = new ThreadManager();
			}
		}
		return instance;
	}
	public ExecutorService getExecutorService() {
		return executorService;
	}
	
}
