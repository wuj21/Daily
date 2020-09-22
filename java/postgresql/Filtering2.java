import java.sql.*;
import java.io.*;
import java.math.*;
import java.utils.*;
class DeletingFilter extends Thread {
    private Connection con = null;
    private int idx;
    
    public DeletingFilter(int i) {
        idx = i;
    }
    public void run() {
        try {
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection("jdbc:postgresql://10.152.11.30:5432/jack","jack","");
            String sq0 = "select max(a), min(a) from d0_" + String.valueOf(idx);
            String sq1 = "select a from d0_" + String.valueOf(idx) + " where a>=? and a<=?";
            String sq2 = "delete from d0_" + String.valueOf(idx) + " where a=?";
            PreparedStatement ps0 = con.prepareStatement(sq0);
            ResultSet rs0 = ps0.executeQuery();
            long bmax = 0, bmin = 0;
            if (rs0.next()) {
                bmax = rs0.getLong(1);
                bmin = rs0.getLong(2);
            }
            long step = (bmax - bmin) / 10 + 2;
            System.out.println(bmax+ " :" + bmin + ":" + step);
            PreparedStatement ps1 = con.prepareStatement(sq1);
            PreparedStatement ps2 = con.prepareStatement(sq2);
            long sum = 0;
            long processed = 0;
            long start = System.currentTimeMillis();
            for (int ii = 0; ii < 10; ii++) {
                ps1.setLong(1, bmax - (ii + 1) * step);
                ps1.setLong(2, bmax - ii * step);
                ResultSet rs = ps1.executeQuery();
                List<Long> primes = new ArrayList<Long>();
                while (rs.next()) {
                    long l = rs.getLong(1);
                    BigInteger bi = BigInteger.valueOf(l);
                    /*
                    String cmd = "/usr/bin/factor " + String.valueOf(l);

                    Process p = Runtime.getRuntime().exec(cmd);
                    p.waitFor();
                    BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
                    String res = br.readLine().trim();
                    int mi = res.indexOf(":");
                    String factors = res.substring(mi + 1).trim();
                    int si = factors.indexOf(" ");
                    if (-1 != si) {
                        long f1 = Long.parseLong(factors.substring(0, si));
                        if ((l != f1) && (l % f1 == 0)) {
                            ps2.setLong(1, l);
                            ps2.executeUpdate();
                            sum ++;
                        } else {
                            System.out.println("Error");
                        }
                    }*/
                    if (false == bi.isProbablePrime(2)) {
                        primes.add(l);
                        //ps2.setLong(1, l);
                        //sum += (ps2.executeUpdate());
                    }
                    processed ++;
                    if (processed % 100000 == 0) {
                        long time = System.currentTimeMillis() - start;
                        if (0 == time) {
                            time = 1;
                        }
                        System.out.println(this.getName() 
                                    + ": processed " + String.valueOf (processed) 
                                    + " rows and deleted " + String.valueOf(sum) + " rows in "
                                    + String.valueOf (time / 1000 / 60) + " minutes." 
                                    + "processing speed " + String.valueOf (processed * 60 / (time / 1000))
                                    + " and deleted speed " + String.valueOf (sum * 60 / (time / 1000)));
                    }
                }
                StringBuffer sqlbuf = new StringBuffer();
                sqlbuf.append("delete from d0_");
                sqlbuf.append(String.valueOf(idx));
                sqlbuf.append(" where (a=-1) ");
                for (long ll: primes) {
                    sqlbuf.append (" or (a= ");
                    sqlbuf.append (String.valueOf(ll));
                    sqlbuf.append(" ) ");
                }
                System.out.println(sqlbuf.toString());
                long time = System.currentTimeMillis() - start;
                if (0 == time) {
                    time = 1;
                }
                System.out.println(this.getName() 
                            + ": processed " + String.valueOf (processed) 
                            + " rows and deleted " + String.valueOf(sum) + " rows in "
                            + String.valueOf (time / 1000 / 60) + " minutes." 
                            + "processing speed " + String.valueOf (processed * 60 / (time / 1000))
                            + " and deleted speed " + String.valueOf (sum * 60 / (time / 1000)));

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { 
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
public class Filtering2{
    public static void main(String[] args) {

        Thread[] ts = new DeletingFilter[256];
        for (int i = 0; i < 256; i++) {
            ts[i] = new DeletingFilter(i);
        }
        ts[100].start();
        /*
        for (int i = 15; i > 1; i--) {
            for (int j = 0; j < 16 ; j++) {
                ts[i * 16 + j].start();
            }
            for (int j = 0; j < 16; j++) {
                try {
                    ts[i * 16 + j].join();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }*/
	/*
        for (int i = 0; i < 12; i++) {
            try {
                ts[i].join();
            } catch (Exception e) {
                e.printStackTrace();
            }           
        }*/
        // for (int i = 6; i < 12; i++) {
        //     ts[i].start();
        // }
        // for (int i = 6; i < 12; i++) {
        //     try {
        //         ts[i].join();
        //     } catch (Exception e) {
        //         e.printStackTrace();
        //     }           
        // }
    }
}

