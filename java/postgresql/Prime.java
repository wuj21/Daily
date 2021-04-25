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
        long [] primes = { 
	23603,
 23609,
 23623,
 23627,
 23629,
 23633,
 23663,
 23669,
 23671,
 23677,
 23687,
 23689,
 23719,
 23741,
 23743,
 23747,
 23753,
 23761,
 23767,
 23773,
 23789,
 23801,
 23813,
 23819        
};
                        
        Thread[] primethreads = new PrimeThread[primes.length];
        for (int i = 23; i >=0; i--) {
            primethreads[i] = new PrimeThread(primes[i]);
            primethreads[i].start();
        }
        boolean isEnded = false;
        while (!isEnded) {
            try {
                Thread.sleep (1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            isEnded = true;
            for (Thread t: primethreads) {
                if (t.isAlive()) {
                    isEnded = false;
                }
            }
        }
        System.out.println("done!");
    }
}

class PrimeThread extends Thread {
    private Connection con = null;
    private PreparedStatement ps = null;
    private long p;
    private static final long MAX = 68719476733L;
    public PrimeThread(long n) {
        p = n;
        try {
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection("jdbc:postgresql://10.152.11.30:5432/jack","jack",""); 
            //            con.setAutoCommit(false);
            ps = con.prepareStatement("delete from b0 where a=?");
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {
        long s = p + p + p;
        long sum = 1;
        long start = System.currentTimeMillis();
        try {
            while (s <= MAX) {
                /*
                if (s % 2 == 0 ) {
                    s += p;
                    }*/
                ps.setLong(1, s);
                sum += ps.executeUpdate();
                //sum += 1;
                //                System.out.println(getName() + ":" + s + ":" + sum);
                s += (p + p);
                if (sum % 10000 == 0) {
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
