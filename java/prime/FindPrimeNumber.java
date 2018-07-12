import java.util.*;
class NumPrime {
    int idx;
    boolean isPrime = true;
    public NumPrime(int n) {
        idx = n;
    }
}
public class FindPrimeNumber {
    public static void main(String[] args) {
        final int N = Integer.MAX_VALUE / 4;
        NumPrime[] all = new NumPrime[N];
        System.out.println(N);

        for (int i = 0; i < N; i++) {
            if (i % 10000000 == 0) {
                System.out.print(i + "->");
            }
            all [i] = new NumPrime(i + 1);
        }
        all[0].isPrime = false;

        System.out.println("\n");

        for (int i = 0; i < 24576; i++) {
            System.out.println(i);

            int s = all.size();
            for (int j = 0; j < s; j++) {
                if (j % 10000 == 0) {
                    System.out.print(j + "-->");
                }
                int t = all.get(j);
                if (t > i && t % i ==0) {
                    all.remove(j);
                }
            }
            System.out.println("\n");
        }
        for (int i = 0; i < all.size(); i++) {
            System.out.print(all.get(i) + ",");
        }
    }

}
