package com.maingalaxy.photo;
import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.maingalaxy.dupfinder.util.MD5Summer;
import sun.security.provider.MD5;

import java.io.*;
import java.util.*;

/**
 * Created by jwu on 2/2/16.
 */
public class PhotoSelector {
    public static void main(String[] args) {
        String rootDir = "/Volumes/shared/photo";
        String targetbase = "/Volumes/myphotos/8th";
        PhotoSelector p = new PhotoSelector();
        List<File> photos = new ArrayList<>();
        p.findPhoto(new File(rootDir), photos);
        System.out.println("Found photos " + photos.size());
        int n = 0;
        String current = "";
        for (int i = photos.size() - 1; i >= 0; i--) {
            File f = photos.get(i);
            if (!current.equals(f.getParentFile().getAbsolutePath())) {
                current = f.getParentFile().getAbsolutePath();
                System.out.println("Processing " + current);
            }
            ImageInfo inf = p.retrieveImageInfo(f);
            p.copyImageFile(f, inf, new File(targetbase));
            n++;
            if (n % 100 == 0) {
                System.out.println("Finished " + String.valueOf(n) + " photos");
            }
        }
//        File image = new File(rootDir + "photo/iphone-7-7/IMG_1275.jpg");
//        try {
//            Metadata imageMd = ImageMetadataReader.readMetadata(image);
//            for (Directory d: imageMd.getDirectories()) {
//                System.out.println(d.getName());
//                for (Tag t: d.getTags()) {
//                    System.out.println(t);
//                }
//            }
//
//        } catch (ImageProcessingException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public void findPhoto(File dir, List<File> images) {
        if (dir.isFile()) {
            return;
        } else {
            System.out.println("Findding photos in " + dir.getAbsolutePath());
            File[] contents = dir.listFiles();
            for (File f: contents) {
                if (f.isFile()) {
                    if (f.getName().toLowerCase().endsWith(".jpg")) {
                        images.add(f);
                    }
                } else {
                    findPhoto(f, images);
                }
            }
        }
        return;
    }

    public boolean fileEquals (File f1, File f2) {
        if (f1.length() != f2.length()) {
            return false;
        }
        String f1sum = "";
        String f2sum = "";
        try {
            f1sum = MD5Summer.getMD5Checksum(f1);
            f2sum = MD5Summer.getMD5Checksum(f2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return f1sum.equals(f2sum);
    }

    public void copyFile(File from, File to) throws IOException {
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            bis = new BufferedInputStream (new FileInputStream(from));
            bos = new BufferedOutputStream (new FileOutputStream(to));
            byte[] buf = new byte[8 * 1048576];
            int len = -1;
            while (-1 != (len = bis.read(buf))) {
                bos.write(buf, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != bis) {
                try {
                    bis.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (null != bos) {
                try {
                    bos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public void copyImageFile(File from, ImageInfo inf, File targetBase) {
        String toFileNameDir = targetBase.getAbsolutePath() + "/" + inf.getYear() + "/" +inf.getYear() + '-' + inf.getMonth() + "-" + inf.getDay();
        File toDir = new File (toFileNameDir);
        if (!toDir.exists()) {
            toDir.mkdirs();
        }
        File to = new File(toFileNameDir + "/" + from.getName());
        while (to.exists() && !fileEquals(from, to)) {
            String fn = to.getName();
            int idx = fn.lastIndexOf(".");
            String base = fn.substring(0, idx);
            System.out.println("debug1:" + fn + "," + base);
            int bk = 2;
            if (-1 != base.lastIndexOf("--")) {
                try {
                    bk = Integer.parseInt(base.substring(base.lastIndexOf("--") + 2)) + 1;
                    base = base.substring(0, base.lastIndexOf("--"));
                } catch (Exception e) {

                }
            }
            String ext = fn.substring(idx + 1);
            System.out.println("debug2:" + base + "," + String.valueOf(bk) + "," + ext);
            to = new File (to.getParent() + "/" + base + "--" + String.valueOf(bk) + "." + ext);
            System.out.println("debug3:" + to.getName());
        }
        if (!to.exists()) {
            try {
                copyFile(from, to);
//                if (false == fileEquals(from, to)) {
//                    throw new RuntimeException("Copying failure");
//                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public ImageInfo retrieveImageInfo(File img) {
        String y = "1977";
        String m = "12";
        String d = "14";
        String dev = "Unknown";
        try {
            if (img.exists()) {

                Metadata md = ImageMetadataReader.readMetadata(img);
                Calendar c = new GregorianCalendar();
                if (null == md) {
 //                   Calendar c = new GregorianCalendar();
                      c.setTimeInMillis(img.lastModified());
//                    y = String.valueOf(c.get(GregorianCalendar.YEAR));
//                    m = String.valueOf(c.get(GregorianCalendar.MONTH));
//                    d = String.valueOf(c.get(GregorianCalendar.DAY_OF_MONTH));
                } else {
                    try {
                        ExifIFD0Directory d0 = md.getFirstDirectoryOfType(ExifIFD0Directory.class);
                        if (null != d0) {
                            dev = d0.getString(ExifIFD0Directory.TAG_MAKE);
                            if (null == dev || "".equals(dev.trim())) {
                                dev = "Unknown";
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        ExifSubIFDDirectory d1 = md.getFirstDirectoryOfType(ExifSubIFDDirectory.class);
//                    Calendar cal = new GregorianCalendar();

                            c.setTimeInMillis(d1.getDateOriginal().getTime());

                    } catch (Exception e) {
//                        e.printStackTrace();
                        c.setTimeInMillis(img.lastModified());
                    }

                }
                y = String.valueOf(c.get(GregorianCalendar.YEAR));
                int mon = c.get(GregorianCalendar.MONTH);
                if (mon < 9) {
                    m = '0' + String.valueOf(1 + mon);
                } else {
                    m = String.valueOf(1 + mon);
                }
                int day = c.get(GregorianCalendar.DAY_OF_MONTH);
                if (day <= 9) {
                    d = "0" + String.valueOf(day);
                } else {
                    d = String.valueOf(day);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ImageInfo(dev.trim(), y, m, d);
    }

}



class ImageInfo {
    private String device = "Unknown";
    private String year;
    private String month;
    private String day;

    public ImageInfo(String dev, String y, String m, String d) {
        device = dev;
        year = y;
        month = m;
        day = d;
    }

    public String getDevice() {
        return device;
    }

    public String getYear() {
        return year;
    }

    public String getMonth() {
        return month;
    }
    public String getDay() {
        return day;
    }
}
