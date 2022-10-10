import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ReceiveHelper extends Thread {

    private final DataInputStream inputStream;

    public ReceiveHelper(InputStream inputStream) {
        this.inputStream = new DataInputStream(inputStream);
    }

    public void run() {
        try {
            while (true) {
                String s = inputStream.readUTF();
                System.out.println(" : " + s);
                System.out.println("----------------------------------------------------");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

