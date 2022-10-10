package client;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ReceiveHelper extends Thread{

    private InputStream inputStream;
    private String type;

    public ReceiveHelper(InputStream inputStream,String type) {
        this.inputStream=inputStream;
        this.type=type;
    }

    public void run() {
        byte[] bytes = new byte[1024];
        int length;
        try {
            while (true){
                if (((length=inputStream.read(bytes))>1)){
                    String s = new String(bytes, 0, length);
                    LocalDateTime nowLocalDate = LocalDateTime.now();
                    String time = nowLocalDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    System.out.println("     "+time+"       "+type+" : "+s);
                    System.out.println("____________________________________________");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

