package cn.com.oasis;

import java.util.ArrayList;
import java.util.concurrent.Future;

/**
 * 介绍: ${描述}
 * 作者: jacky
 * 邮箱: hewei@incoming.cn
 * 时间:  16/10/15 下午12:06
 */

public class MaintTest {
    public static void main(String[] args) {

        ArrayList<Future> futures = new ArrayList<>();
        Future future1 = ImAsyncExecutor.getInstance().submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("tastk1");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        futures.add(future1);

        final Future future2 =  ImAsyncExecutor.getInstance().submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("tastk2");

                try {
                    Thread.sleep(1000);
                    System.out.println("tastk2");
                    //ImAsyncExecutor.getInstance().cancleAll();
                    Thread.sleep(3000);
                    System.out.println("after cancle");
                } catch (InterruptedException e) {
                    //e.printStackTrace();
                }
            }
        });

        futures.add(future2);

        final Future future3 =  ImAsyncExecutor.getInstance().submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("tastk3");
                ImAsyncExecutor.getInstance().cancleAll();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        futures.add(future3);

        final Future future4 = ImAsyncExecutor.getInstance().submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("tastk4");

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        futures.add(future4);

        try {
            Thread.sleep(8000);
            //future2.cancel(true);

            System.out.println("cancel all");
            for (Future f : futures){
                if(!f.isDone()){
                    f.cancel(true);
                }else {
                    System.out.println("is down");
                }
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
