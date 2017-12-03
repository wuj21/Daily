package com.maingalaxy.threadpool;


/**
 * Created by jwu on 5/18/15.
 */
public class PoolThread extends Thread {
    private volatile boolean stopFlag = false;
    private ThreadTask task = null;
    private volatile boolean isWorking = false;
    public PoolThread () {

    }
    public void run() {
        while (!stopFlag) {
            if (task == null || isWorking == false) {
                try {
                    sleep(1000);
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                }
            } else {
                long ttt = 0;
                try {
                    ttt = System.currentTimeMillis();
                    System.out.println("Thread " + this.getName() + ":" + task.getName() + "started!");
                    task.run();
                } catch (Throwable t) {
                    t.printStackTrace();
                } finally {
                    long e = System.currentTimeMillis() - ttt;
                    System.out.println("Thread " + this.getName() + ":" + task.getName() + " finished with " + e + "milliseconds with " + (task.getMetrics() / (e / 1000.0)));
                    isWorking = false;
                    task = null;
                }
            }
        }
    }

    public void assignTask(ThreadTask task) {
        isWorking = true;
        this.task = task;
    }

    public void runTask() {
        this.isWorking = true;
    }

    public boolean isWorking() {
        return isWorking;
    }

    public void destroyThread() {
        stopFlag = true;
    }
}
