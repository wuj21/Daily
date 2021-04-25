import java.sql.*;
class DeletingFilter extends Thread {
    private Connection con = null;
    private long min, max;
    long[] factors;
    public DeletingFilter(Connection c, long min, long max, long[] f) {
        con = c;
        factors = f;
        this.min = min;
        this.max = max;
    }
    public void run() {
        try {
            PreparedStatement ps1 = con.prepareStatement("select a from c0 where a>=? and a<? order by a ");
            ps1.setLong(1, min);
            ps1.setLong(2, max);
            ResultSet rs = ps1.executeQuery();
            PreparedStatement ps2 = con.prepareStatement("delete from c0 where a=?");
            long sum = 0;
            long processed = 0;
            long start = System.currentTimeMillis();
            while (rs.next()) {
                long l = rs.getLong(1);
                int i = 0;
                while (factors[i] <= l / factors[i]) {
                    if (l % factors[i] == 0) {
                        //System.out.println(l + "is not Prime Number!") ;
                        ps2.setLong(1, l);
                        ps2.executeUpdate();
                        sum ++;
                        break;
                    }
                    i++;
                    if (i >= factors.length) {
                        break;
                    }
                }
                processed ++;
                if (processed % 1000 == 0) {
                    long time = (System.currentTimeMillis() - start) / 1000 / 60;
                    System.out.println(this.getName() 
                                + ": processed " + String.valueOf (processed) 
                                + " rows and deleted " + String.valueOf(sum) + " rows in "
                                + String.valueOf (time) + " minutes." 
                                + "processing speed " + String.valueOf (processed / time)
                                + " and deleted speed " + String.valueOf (sum / time));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
}
public class Filtering{
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
            PreparedStatement ps3 = con.prepareStatement("delete from c0 where a=?");
            ps2.setLong(1, 17483387L);
            ps2.setLong(2, 3037000500L);
            long[] factors = new long[145023943];
            
            ResultSet rs2 = ps2.executeQuery();
            int pi = 0;
            while (rs2.next()) {
                factors[pi] = rs2.getLong(1);
                pi++;
            }
            System.out.println("factors done");
            long start = 9223371968135299041L;
            long step = 2863311531L;
            Thread[] ts = new DeletingFilter[12];
            for (int i = 0; i < 12; i++) {
                ts[i] = new DeletingFilter(con, start + i * step, start + (i + 1) * step, factors);
                //System.out.println(String.valueOf(start + i*step) + ":" + String.valueOf(start + (i + 1) * step));
            }
            for (int i = 0; i < 12; i++) {
                ts[i].start();
            }
            for (int i = 0; i < 12; i++) {
                ts[i].join();
            }
            /*
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
                if (sum % 1000 == 0) {
                    System.out.println("finished " + sum );
                }

            }*/
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
    }
}

