package cn.com.oasis;

import java.util.concurrent.SynchronousQueue;

public class SynchronousQueueDemo {
    public static void main(String[] args) throws InterruptedException {
        final SynchronousQueue queue = new SynchronousQueue();


        new Thread() {
            @Override
            public void run() {
                try {
                    boolean a = true;
                    while (a) {
                        System.out.println("element:" + queue.take());
                        a = false;
                    }
                } catch (InterruptedException e) {

                    e.printStackTrace();
                }
            }
        }.start();

        // offer add  put 差别
        // add 如果若超出了度列的长度会直接抛出异常：
        // put 会等待
        // offer : 如果发现队列已满无法添加的话，会直接返回false


        // poll 如果队列为空, 返回null
        // remove 如果队列为空抛出异常
        // take 如果队列为空发生阻塞, 等待元素
        for (int i = 0; i < 10; i++) {
            queue.put("hello" + i);
            System.out.println("put num:" + i);
        }
    }
}
