package com.maingalaxy.dupfinder.util.test;

import com.maingalaxy.dupfinder.util.MD5Summer;
import org.junit.Test;

import java.io.File;
import java.io.RandomAccessFile;

import static org.junit.Assert.*;

public class MD5SummerTest {

    @Test
    public void testGetMD5Checksum() throws Exception {
        File tempfile = File.createTempFile("test", "txt");
        //File tempfile = new File("/Users/jwu/data.txt");

        System.out.println(tempfile.getAbsolutePath());
        RandomAccessFile ras = new RandomAccessFile(tempfile, "rw");
        ras.writeBytes("123\n");
        ras.close();

        String md5sum = MD5Summer.getMD5Checksum(tempfile);
        assertEquals(md5sum, "ba1f2511fc30423bdbb183fe33f3dd0f");

    }
}