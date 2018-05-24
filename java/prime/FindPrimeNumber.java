import java.util.*;
public class FindPrimeNumber {
    public static void main(String[] args) {
        List <Integer> all = new LinkedList<Integer> ();
        System.out.println(Integer.MAX_VALUE);
        for (int i = 3; i < Integer.MAX_VALUE / 4; i++) {
            if (i % 10000000 == 0) {
                System.out.print(i + "->");
            }
            all.add(i);
        }
        System.out.println("\n");
        for (int i = 3; i < 24576; i += 2) {
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
