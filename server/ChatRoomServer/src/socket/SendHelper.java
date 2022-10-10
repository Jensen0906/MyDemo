package socket;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;

public class SendHelper extends Thread{

    private String message;
    private List<Socket> list;
    private int id;

    public SendHelper(String message, List<Socket> list, int id) {
        this.message = message;
        this.list = list;
        this.id = id;
    }

    public void run() {
        try {
            for (int i=0; i < list.size(); i++) {
                if (i+1 == id) continue;
                OutputStream outputStream = list.get(i).getOutputStream();
                outputStream.write(message.getBytes());
                outputStream.flush();
                outputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}

