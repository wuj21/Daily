package com.maingalaxy.javalearning;

/**
 * Created by jwu on 10/27/16.
 */
public class BinarySearcher {
    public static int search(int[] src, int n) {
        int l = 0;
        int h = src.length - 1;
        while (l <= h) {
            int m = (l + h) / 2;
            if (src[m] == n) {
                return m;
            } else if (src[m] > n) {
                h = m - 1;
            } else {
                l = m + 1;
            }
        }
        return -1;
    }
}
