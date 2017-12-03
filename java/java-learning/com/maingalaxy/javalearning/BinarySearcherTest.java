package com.maingalaxy.javalearning;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by jwu on 10/27/16.
 */
public class BinarySearcherTest {
    private static final int N = 100;
    @Test
    public void testWithNoDupNumber() {
        for (int j = 1; j <= N; j++) {
            int[] data = new int[j];
            for (int i = 0; i < j; i++) {
                data[i] = i;
            }
            for (int i = 0; i < j; i++) {
                Assert.assertEquals(i, BinarySearcher.search(data, i));
            }

            Assert.assertEquals(-1, BinarySearcher.search(data, -1));
            Assert.assertEquals(-1, BinarySearcher.search(data, j));
        }
    }

    @Test
    public void testWithDupNumber() {
        int[] data = new int [2 * N];
        for (int i = 0 ; i < N; i ++){
            data [2 * i] = i;
            data [2 * i + 1] = i;
        }
        for (int i = 0; i < N ; i++) {
            int idx = BinarySearcher.search(data, i);
            Assert.assertEquals(i, data[idx]);
        }
    }
}