package cn.com.oasis;


import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * 介绍: ${描述}
 * 作者: jacky
 * 邮箱: hewei@incoming.cn
 * 时间:  16/12/6 下午5:04
 */

public class FutureTest {

    private Executor executor;

    public static class Bean {
        public String content;

        public String getContent() {
            return content;
        }

        public Bean(String content) {
            this.content = content;
        }
    }


    public static class LoadTaks implements Callable<Bean> {
        private String content;

        public LoadTaks(String content) {
            this.content = content;
        }

        @Override
        public Bean call() throws Exception {
            System.out.println("sleep 5000");
            System.out.println("thread name:" + Thread.currentThread().getName());
            Thread.sleep(5000);
            return new Bean(content);
        }
    }

    private FutureTask<Bean> init() {
        executor = Executors.newSingleThreadExecutor();
        LoadTaks task = new LoadTaks("123454");
        FutureTask<Bean> tasks = new FutureTask<Bean>(task);
        executor.execute(tasks);
        return tasks;
    }

    public static void main(String[] args) {
        System.out.println("start");

        FutureTest test = new FutureTest();
        FutureTest test1 = new FutureTest();
        System.out.println(test + "");
        System.out.println(test1 + "");


        /*FutureTask<Bean> tasks = test.init();

        try {
            tasks.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        System.out.println("thread name:" + Thread.currentThread().getName());

        System.out.println("over");*/

    }
}
