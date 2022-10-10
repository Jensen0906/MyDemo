import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ReceiveHelper extends Thread{

    private InputStream inputStream;

    public ReceiveHelper(InputStream inputStream) {
        this.inputStream=inputStream;
    }

    public void run() {
        byte[] bytes = new byte[1024];
        int length;
        try {
            while (true){
                if (((length=inputStream.read(bytes))>1)){
                    String s = new String(bytes, 0, length);
                    System.out.println(" : "+s);
                    System.out.println("----------------------------");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

