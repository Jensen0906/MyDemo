package socket;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ReceiveHelper extends Thread{

    private InputStream inputStream;
    private List<Socket> list;
    private int id;

    public ReceiveHelper(InputStream inputStream, List<Socket> list, int id) {
        this.inputStream=inputStream;
        this.list = list;
        this.id = id;
    }

    public void run() {
        byte[] bytes = new byte[1024];
        int length;
        try {
            while (true){
                if (((length=inputStream.read(bytes))>1)){
                    String s = new String(bytes, 0, length);
                    new SendHelper(s, list, id).start();
                    System.out.println(id+" : "+s);
                    System.out.println("---------------------------");
                }
            }
        } catch (IOException e) {
           // e.printStackTrace();
        }
    }

}

