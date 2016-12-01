package cn.com.oasis;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 介绍: ${描述}
 * 作者: jacky
 * 邮箱: hewei@incoming.cn
 * 时间:  16/10/15 下午12:05
 */

public class ImAsyncExecutor {
    private static long KEEP_ALIVE_TIME = 60L;
    private static ThreadPoolExecutor executor;
    private static ImAsyncExecutor instance;
    private static final int WORK_QUEUE_SIZE = 100; //任务队列尺寸
    private static ArrayList<Future> futures = new ArrayList<>();
    private static ArrayBlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<Runnable>(WORK_QUEUE_SIZE);
    private static ThreadPoolExecutor defaultExcutor(){
        return  new ThreadPoolExecutor(
                1, 1,
                KEEP_ALIVE_TIME, TimeUnit.MILLISECONDS,
                workQueue
        );
    }

    public static ImAsyncExecutor getInstance() {
        if (instance == null) {
            synchronized (ImAsyncExecutor.class) {
                if (instance == null) {
                    instance = new ImAsyncExecutor();
                }
            }
        }
        return instance;
    }
    private ImAsyncExecutor() {
        executor = defaultExcutor();
    }

    public void execute(Runnable runnable){
        executor.execute(runnable);
    }


    public Future submit(Runnable runnable){
        update();
        Future future = executor.submit(runnable);
        futures.add(future);
        return future;
    }

    public void update(){
        ArrayList <Future> delFutureList = new ArrayList<>();
        for (Future future :futures){
            if (future.isDone()){
                delFutureList.add(future);
            }
        }
        futures.removeAll(delFutureList);
    }

    public void cancleAll(){
        for (Runnable runnable : workQueue){
            executor.remove(runnable);
        }

        for (Future future : futures){
            if (!future.isDone()){
                future.cancel(true);
            }
        }
    }
}
