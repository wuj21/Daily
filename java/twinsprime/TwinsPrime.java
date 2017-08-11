public class TwinsPrime {
    public static void main(String[] args) {
        
    }

    private static long intSquire (long l) {
        long low = 3;
        long high = l / 2 + 1;
        while (low < high) {
            if (high * high > l) {
                high = (high + low) / 2;
            }
            if (low * low < l) {
                low = (high + low) / 2;
            }
        }
        return high;
    }

}
