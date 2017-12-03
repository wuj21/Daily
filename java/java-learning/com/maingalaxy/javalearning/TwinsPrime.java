package com.maingalaxy.javalearning;

/**
 * Created by jwu on 4/20/17.
 */

public class TwinsPrime {
    private static boolean isPrime(long l) {
        for (long i = 3; i < l / 2; i += 2) {
            if (l / i < i) {
                break;
            }
            if (l % i == 0) {
//                System.out.println(l + " can be divided by " + i);
                return false;
            }
        }
        System.out.println(l + " is Prime NUmber");
        return true;
    }

    public static void main (String[] args) {
//        long m = Long.MAX_VALUE;
        long m = 9223372036854742397L - 2;
        if (m % 2 == 0) {
            m--;
        }
        long count = 1;
        for (long i = m; i > 5; i = i - 2) {
//            System.out.println(count + ":evaluating " + i + "," + (i - 2));
            count ++;
            if (i % 10 == 7) {
                continue;
            }
            if (isPrime(i)) {
                if (isPrime(i - 2)) {
                    System.out.println("Twins Prime: " + i + " and " + (i - 2));
                    break;
                } else {
//                    System.out.println("here");
                    i -= 2;
                }
            }
        }
    }
}
