import java.io.File;
import java.io.RandomAccessFile;

public class TestGit {
	public static void main(String[] args) throws Exception {

        System.out.println("Test Git");

        RandomAccessFile ras = new RandomAccessFile(new File("/Users/jwu/ras.txt"), "rw");
        ras.writeBytes("123\n");
        ras.writeChars("123\n");
        ras.writeUTF("123\n");
        ras.close();
	}
}
