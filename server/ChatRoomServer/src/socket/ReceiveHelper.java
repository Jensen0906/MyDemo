package socket;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.List;

public class ReceiveHelper extends Thread {

    private final DataInputStream inputStream;
    private final List<Socket> list;
    private final int id;

    public ReceiveHelper(InputStream inputStream, List<Socket> list, int id) {
        this.inputStream = new DataInputStream(inputStream);
        this.list = list;
        this.id = id;
    }

    public void run() {
        byte[] bytes = new byte[1024];

    }

}

