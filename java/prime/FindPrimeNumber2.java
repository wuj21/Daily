import java.util.*;

public class FindPrimeNumber2 {
    public static void main (String[] args) {
        List<Long> primes = new ArrayList<>();
        primes.add(2L);
        long idx = 1;
        for (long i = 3L; i < Long.MAX_VALUE; i += 2L) {
            boolean isPrime = true;
            for (int j = 0; primes.get(j) <= i / primes.get(j); j++) {
                if (i % primes.get(j) == 0) {
                    isPrime = false;
                    break;
                }
            }
            if (isPrime) {
                idx ++;
                System.out.println (idx + ":" + i + ":" + Double.toString(idx * 1.0 / (i * 1.0)));
                primes.add(i);
            }
        }
    }
    /*
    private static int truc_squire(int i) {
        if (i < 0) {
            return -1;
        }
        int l, h, m;
        l = 1;
        h = i;
        m = (l + h) / 2;
        while (l < h) {
            System.out.println(l + ":" + h + ":" + m);
            m = (l + h) / 2;
            if (i / m == m && i % m == 0) {
                return m;
            }
            if (i / m > m) {
                l = m + 1;
            } else {
                h = m - 1; 
            }
        }
        if (l == h) { m = (l + h) / 2;}
        return m;
        }*/
}
