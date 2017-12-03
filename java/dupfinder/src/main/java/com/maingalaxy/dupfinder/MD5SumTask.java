package com.maingalaxy.dupfinder;

import com.maingalaxy.dupfinder.util.MD5Summer;
import com.maingalaxy.threadpool.ThreadTask;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by jwu on 5/26/15.
 */
public class MD5SumTask implements ThreadTask {
    private File file = null;
    private String md5sum = "";
    private Map<String, List<File>> dupmap = null;
    public MD5SumTask(File f, Map<String, List<File>> map) {
        file = f;
        dupmap = map;
    }
    public void run() {
        try {
            md5sum = MD5Summer.getMD5Checksum(file);
            synchronized (dupmap) {
                if (!dupmap.keySet().contains(md5sum)) {
                    dupmap.put(md5sum, new LinkedList<File>());
                }
                dupmap.get(md5sum).add(file);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public double getMetrics() { return file.length() / 1000.0 / 1000.0;}

    public String getMd5sum() {
        return md5sum;
    }

    public String getName() {
        return "Task " + file.getAbsolutePath() + " with " + file.length() + " bytes ";
    }
}
