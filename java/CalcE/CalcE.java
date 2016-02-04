public class CalcE {
    public static void main(String[] args){
        for (int i = 1; i < 10000; i++) {
            double e = Math.pow(1.0 + 1.0 / i, i * 1.0);
            System.out.println(String.valueOf(e) + ":" + String.valueOf(Math.E - e));
        }
    }
}
