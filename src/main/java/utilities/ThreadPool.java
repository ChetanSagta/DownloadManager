package utilities;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.concurrent.*;

public class ThreadPool {

    ExecutorService executorService = null;
    BlockingQueue<Runnable> blockingQueue = null;
    private static ThreadPool threadPool = null;

    public static ThreadPool getInstance() {
        if (threadPool == null)
            threadPool = new ThreadPool();
        return threadPool;
    }

    private ThreadPool() {
        blockingQueue = new ArrayBlockingQueue<>(1024);
        executorService = new ThreadPoolExecutor(10, 30, 5, TimeUnit.SECONDS, blockingQueue);
    }

    public void queueIntoBlockingQueue(Runnable task) {
        try {
            blockingQueue.put(task);
        } catch (InterruptedException ex) {
            logger.info("Could not enter into blocking queue due to  : " + ex);
            Thread.currentThread().interrupt();
        }
    }

    public void executeTask(Runnable task){
        executorService.execute(task);
    }

    private static final Logger logger = LogManager.getLogger(ThreadPool.class.getSimpleName());

}
