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
                    System.out.println("Thread " + this.getName() + " run task " + task.getName());
                    task.run();
                } catch (Throwable t) {
                    t.printStackTrace();
                } finally {
                    System.out.println("Thread " + this.getName() + "run task " + task.getName() + " finished with " + (System.currentTimeMillis() - ttt) + "milliseconds");
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
