import java.sql.*;
import java.io.*;
import java.math.*;
import java.util.*;
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
            String sq0 = "select max(a), min(a) from d0_0";
            String sq1 = "select a from d0_0 where a>=? and a<=? order by a desc";
            String sq2 = "delete from d0_0 where a=?";
            PreparedStatement ps0 = con.prepareStatement(sq0);
            ResultSet rs0 = ps0.executeQuery();
            long bmax = 0, bmin = 0;
            if (rs0.next()) {
                bmax = rs0.getLong(1);
                bmin = rs0.getLong(2);
            }
            long step = (bmax - bmin) / 80 + 2;
            System.out.println(bmax+ " :" + bmin + ":" + step);
            PreparedStatement ps1 = con.prepareStatement(sq1);
            PreparedStatement ps2 = con.prepareStatement(sq2);
            long sum = 0;
            long processed = 0;
            long start = System.currentTimeMillis();
            ps1.setLong(1, bmax - (idx + 1) * step);
            ps1.setLong(2, bmax - idx * step);
            ResultSet rs = ps1.executeQuery();
                        
            while (rs.next()) {
                            
                long l = rs.getLong(1);
                BigInteger bi = BigInteger.valueOf(l);
                if (false == bi.isProbablePrime(2)) {
                    ps2.setLong(1, l);
                    sum += (ps2.executeUpdate());      
                }
                            
                processed ++;
                if (processed % 100000 == 0) {
                    long time = (System.currentTimeMillis() - start) / 1000;
                    if (0 == time) {
                        time = 1;
                    }
                    System.out.println(getName() 
                        + ": processed " + String.valueOf (processed) 
                        + " rows and deleted " + String.valueOf(sum) + " rows in "
                        + String.valueOf (time / 60) + " minutes." 
                        + "processing speed " + String.valueOf (processed * 60 / (time))
                        + " and deleted speed " + String.valueOf (sum * 60 / (time)));
                }
            }
                        
                
            long time = (System.currentTimeMillis() - start) / 1000;
            if (0 == time) {
                time = 1;
            }
            System.out.println(this.getName() 
                            + ": processed " + String.valueOf (processed) 
                            + " rows and deleted " + String.valueOf(sum) + " rows in "
                            + String.valueOf (time / 60) + " minutes." 
                            + "processing speed " + String.valueOf (processed * 60 / (time))
                            + " and deleted speed " + String.valueOf (sum * 60 / (time)));

            

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
public class Filtering5{
    public static void main(String[] args) {
    	try {
            Thread[] ts = new Thread[80];
            for (int i = 0; i < 60; i++) {
                ts[i] = new DeletingFilter(i);
		        ts[i].start();
            }
            for (int i = 0; i < 60; i++) {
                ts[i].join();
            }
	    } catch (Exception e) {
		    e.printStackTrace();
	    }
    }
}

