package com.maingalaxy.dupfinder.util;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by jwu on 2/5/15.
 */
public final class MD5Summer {

    private static final int DEFAULT_BUFSIZE = 8192;

    private MD5Summer() {}

    private static byte[] createMD5Checksum (File filename, int size) throws IOException {
        int bufsize = size > 0? size: DEFAULT_BUFSIZE;
        MessageDigest md5sum = null;
        InputStream is = null;
        try {
            md5sum =  MessageDigest.getInstance("MD5");;
            is = new FileInputStream(filename);

            byte[] buf = new byte[bufsize];

            int c = 0;

            while ((c = is.read(buf)) > 0) {
                md5sum.update(buf, 0 , c);
            }
        } catch (NoSuchAlgorithmException nae) {
            System.out.println("Cannot find MD5 library");

        } finally {
            try { is.close(); } catch (IOException e) {}
        }

        return md5sum.digest();
    }

    public static String getMD5Checksum (File file, int bufsize) throws IOException {
        StringBuilder strbuf = new StringBuilder();
        for (byte b: createMD5Checksum(file, bufsize)) {
            strbuf.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
        }
        return strbuf.toString();
    }

    public static String getMD5Checksum (File file) throws IOException {
        return getMD5Checksum(file, DEFAULT_BUFSIZE);
    }
}
