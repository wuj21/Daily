public class Rate {
    public static void main(String[] args) {
        int[] p1 = {3, 5, 7, 11, 13, 17, 19};
        int[] p2 = {23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73};
        int[] p3 = {79, 83, 89, 97, 101, 103, 107, 109, 113, 127};
        double s1 = 1.0;
        double s2 = 1.0;
        double s3 = 1.0;

        for (int i = 0; i < p1.length; i++) {
            s1 *= (p1[i] - 1) / (p1[i] * 1.0);
            s2 *= (p2[i] - 1) / (p2[i] * 1.0);
            s3 *= (p3[i] - 1) / (p3[i] * 1.0);
        }

        System.out.println(s1 + ":" + s2 + ":" + s3 + ":" + s1 * s2 * s3);
    }
}
