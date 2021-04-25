import java.sql.*;

public class Range{
    public static void main(String[] args) {
        
        Connection con = null;
        long max = 9223372022538218123L;
        long min = 9223372019674906592L;
        long sum = 0;
        try {
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection("jdbc:postgresql://10.152.11.30:5432/jack","jack","");
            PreparedStatement ps1 = con.prepareStatement("select a from c0 limit 100000");
            PreparedStatement ps2 = con.prepareStatement("select a from a0 where a>? and a<=? order by a");
            PreparedStatement ps3 = con.prepareStatement("insert into nono (a) values (?)");
            //ps.setInt(1,5);
            ps2.setLong(1, 2L);
            ps2.setLong(2, 3037000500L);
            long[] factors = new long[146144317];
            ResultSet rs2 = ps2.executeQuery();
            int pi = 0;
            while (rs2.next()) {
                factors[pi] = rs2.getLong(1);
                pi++;
            }
            /*
            for (int i = 0; i < factors.length; i++) {
                factors[i] = rs2.getLong(1);
                }*/
            //Result rs2 = ps2.executeQuery();
            ResultSet rs = ps1.executeQuery();
            while (rs.next()) {
                long a = rs.getLong(1);
                sum ++;
                int i = 0;
                while ( factors[i] <= a / factors[i]) {
                    if (a % factors[i] == 0) {
                        System.out.println(String.valueOf(a) + " is not Prime number! " + String.valueOf(factors[i]) + " is a factor");
                        break;
                    }
                    i++;
                }
/*
                ps2.setLong(1, min / a);
                ps2.setLong(2, max / a);
                ResultSet rs2 = ps2.executeQuery();
                while (rs2.next()) {
                    long  p = rs2.getLong(1);
                    ps3.setLong(1, p * a);
                    ps3.executeUpdate();
                }
                sum ++; */
                if (sum % 1000 == 0) {
                    System.out.println("finished " + sum );
                }

            }
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            if ( null != con ) {
                try {
                    con.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        /*
        long [] primes = {3, 5, 7, 11, 13,
                          17, 19, 23, 29, 31,
                          37, 41, 43, 47, 53,
                          59, 61, 67, 71, 73,
                          79, 83, 89, 97};
        Thread[] primethreads = new PrimeThread[primes.length];
        for (int i = 23; i >=0; i--) {
            primethreads[i] = new PrimeThread(primes[i]);
            primethreads[i].start();
            }*/
    }
}

/*
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
                if (sum % 100000000 == 0) {
                    //      con.commit();
                    //    System.out.println(getName() + ":" + p + " deleted " + sum + " rows during " +(System.currentTimeMillis() - start) );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            
            try {
                con.commit();
            } catch (Exception e) {
                e.printStackTrace();
                }
        }
        long elaps = System.currentTimeMillis() - start;
        System.out.println("Delete " + sum + " rows for " + p + " during " + elaps);
    }
}
*/
