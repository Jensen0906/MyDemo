package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException, InterruptedException {
        Socket socket = null;
        try {
            socket = new Socket("127.0.0.1", 8084);
            
        } catch (Exception exception) {
            System.out.println("连接失败");
            return;
        }
        DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
        Scanner scanner = new Scanner(System.in);
        String msg = scanner.next();
        while (!msg.equals(":q")){
            outputStream.writeUTF(msg);
            msg = scanner.next();
        }
        scanner.close();
        socket.close();
    }
}
