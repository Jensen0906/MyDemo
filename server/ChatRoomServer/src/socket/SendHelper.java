package socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;

public class SendHelper extends Thread{

    private final String message;
    private final List<Socket> list;
    private final int id;

    public SendHelper(String message, List<Socket> list, int id) {
        this.message = message;
        this.list = list;
        this.id = id;
    }

    public void run() {
        try {
            for (int i=0; i < list.size(); i++) {
                if (i+1 == id) continue;
                DataOutputStream outputStream = new DataOutputStream(list.get(i).getOutputStream());
                outputStream.writeUTF(message);
                outputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("send message to failed");
        }
    }
    
}

