package com.maingalaxy.dupfinder;

import com.maingalaxy.threadpool.PoolThread;
import com.maingalaxy.threadpool.ThreadPool;

import java.io.File;
import java.io.IOException;
import java.util.TreeMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by jwu on 3/12/15.
 */
public class DupFinder {

    private Map<String, List<File>> dupMap = new ConcurrentHashMap<>();

    private Map<Long, List<File>> sizeMap = new TreeMap<>();



    private File file = null;

    public DupFinder(File file) {this.file = file;}

    private ThreadPool tp = new ThreadPool(9);


    private void analyseFile(File f) throws IOException {
        if (null == f) {
            return;
        } else if (f.isFile()) {

            //String dig = MD5Summer.getMD5Checksum(f);
            long filesize = f.length();
//            fileDig.put(f, dig);
            if (!sizeMap.keySet().contains(filesize)) {
                sizeMap.put(filesize, new LinkedList<File>());
            }
            sizeMap.get(filesize).add(f);
        } else {
            System.out.println("Processing directory " + f.getAbsolutePath());
            File[] sub = f.listFiles();
            if (null == sub) {
                return;
            }
//            System.out.println(sub.length);
            for (File s: sub) {
//                System.out.println(s.getName());
                analyseFile(s);
            }
        }
    }

    private void analyseFile2() throws IOException {
        for (Map.Entry<Long, List<File>> entry: sizeMap.entrySet()) {
            List<File> files = entry.getValue();
            if (files.size() <= 1) {
                continue;
            }
            for (File f: files) {
                if (f.length() < 1000000) {
                    continue;
                }
//                System.out.println("calculating file md5sum " + f.getAbsolutePath() + "/" + f.getName());
//                String md5sum = MD5Summer.getMD5Checksum(f);
//                if (!dupMap.keySet().contains(md5sum)) {
//                    dupMap.put(md5sum, new LinkedList<File>());
//                }
//                dupMap.get(md5sum).add(f);
                MD5SumTask mt = new MD5SumTask(f, dupMap);
                PoolThread pt = null;
                while ((pt = tp.getFreeThread()) == null) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                pt.assignTask(mt);
                pt.runTask();
            }
        }
    }

    public void findDupFile() throws Exception {
        analyseFile(this.file);
        long totalSize = 0;
        for (Map.Entry<Long, List<File>> entry: sizeMap.entrySet()) {
            List<File> files = entry.getValue();
            if (files.size() <= 1) {
                continue;
            }
            for (File f: files) {
                if (f.length() < 1000000) {
                    continue;
                } else {
                    totalSize += f.length();
                }
            }
        }
        System.out.println("total size is " + totalSize);
        analyseFile2();
        while (this.tp.getBusyThreads() != 0) {
            Thread.sleep (2000);
        }
        this.tp.destroy();
    }

    public Map<String, List<File>> getDupFile() {
        return this.dupMap;
    }

    public static void main(String[] args) throws Exception {
        DupFinder df = new DupFinder(new File("/Volumes/_system_"));
        df.findDupFile();
        Map<String, List<File>> dup = df.getDupFile();
        for (Map.Entry<String, List<File>> entry: dup.entrySet()) {
            String md5 = entry.getKey();
            List<File> files = entry.getValue();
            if (files.size() >= 2) {
                System.out.println("md5sum:" + md5);
                for (File f: files) {
                    System.out.println("|----" + f.getAbsolutePath());
                    if (f.getAbsolutePath().lastIndexOf("temp_temp") != -1) {
                        System.out.println("deleting " + f.getAbsolutePath());
                        f.delete();
                    }
                }
            }
        }
    }
}
