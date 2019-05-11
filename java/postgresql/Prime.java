import java.sql.*;

public class Prime {
    public static void main(String[] args) {
        /*
        Connection con = null;
        try {
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection("jdbc:postgresql://10.152.11.30:5432/jackdb","jack","");
            PreparedStatement ps = con.prepareStatement("select a from a0 where a=?");
            ps.setInt(1,5);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getInt(1));
            }
        }catch (Exception e) {
            e.printStackTrace();
            }*/
        long [] primes = {3, 5, 7, 11, 13,
                          17, 19, 23, 29, 31,
                          37, 41, 43, 47, 53,
                          59, 61, 67, 71, 73,
                          79, 83, 89, 97};
        Thread[] primethreads = new PrimeThread[primes.length];
        for (int i = 11; i >=0; i--) {
            primethreads[i] = new PrimeThread(primes[i]);
            primethreads[i].start();
        }
    }
}

class PrimeThread extends Thread {
    private Connection con = null;
    private PreparedStatement ps = null;
    private long p;
    private static final long MAX = 34359738367L;
    public PrimeThread(long n) {
        p = n;
        try {
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection("jdbc:postgresql://10.152.11.30:5432/jackdb","jack","");
            //            con.setAutoCommit(false);
            ps = con.prepareStatement("delete from a0 where a=?");
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {
        long s = p + p;
        long sum = 0;
        long start = System.currentTimeMillis();
        try {
            while (s <= MAX) {
                if (s % 2 == 0 ) {
                    s += p;
                }
                ps.setLong(1, s);
                ps.executeUpdate();
                sum += 1;
                //                System.out.println(getName() + ":" + s + ":" + sum);
                s += p;
                if (sum % 1000000 == 0) {
                    //      con.commit();
                    System.out.println(getName() + ":" + p + " deleted " + sum + " rows during " +(System.currentTimeMillis() - start) );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            /*
            try {
                con.commit();
            } catch (Exception e) {
                e.printStackTrace();
                }*/
        }
        long elaps = System.currentTimeMillis() - start;
        System.out.println("Delete " + sum + " rows for " + p + " during " + elaps);
    }
}
