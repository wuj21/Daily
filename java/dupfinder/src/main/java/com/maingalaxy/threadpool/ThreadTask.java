package com.maingalaxy.threadpool;

/**
 * Created by jwu on 5/18/15.
 */
public interface ThreadTask {
    public void run();
    public String getName();
    public double getMetrics();
}
