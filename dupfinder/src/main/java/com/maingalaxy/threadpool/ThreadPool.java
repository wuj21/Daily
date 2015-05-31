package com.maingalaxy.threadpool;

/**
 * Created by jwu on 5/18/15.
 */
public class ThreadPool {
    private PoolThread[] threads;
    public ThreadPool(int size) {
        threads = new PoolThread[size];
        for (int i = 0; i < size; i++) {
            threads[i] = new PoolThread();
        }
        for (PoolThread t: threads) {

            t.start();
        }
    }

    public PoolThread getFreeThread() {
        for (PoolThread t: threads) {
            if (!t.isWorking()) {
                return t;
            }
        }
        return null;
    }

    public void destroy () {
        for (PoolThread t: threads) {
            t.destroyThread();
            t = null;
        }
        threads = null;
    }

}
