package socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class ChatSocket {

    private static int onlineNumber = 0;
    private static Map<Integer, Socket> userMap = new HashMap<>(); 

    public static void main(String[] args) {
        System.out.println("start!!!");
        startService();
    }

    private static void startService() {
        try {
            ServerSocket serverSocket = new ServerSocket(8084);
            System.out.println("get socket success, start server now");
            while (true) {
                System.out.println("---等待客户端连接---");
                Socket socket = serverSocket.accept();
                onlineNumber++;
                userMap.put(onlineNumber, socket);
                System.out.println(onlineNumber+"号连接，当前人数：" + userMap.size());
                startReader(onlineNumber, socket);
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
            System.out.println("get socket failed");
        }
    }

    private static void startReader(final int userId, final Socket socket) {
      //  sendMessage(socket, "-- Hello, This is server --");
        new Thread(() -> {
            boolean isConnect = true;
            DataInputStream reader;
            System.out.println("*等待客户端输入*");
            while (true && isConnect) {
                try {
                    reader = new DataInputStream(socket.getInputStream());
                    String msg = reader.readUTF();
                    System.out.println(userId +"号：" + msg);
                    sendMessage(socket, msg);
                } catch (IOException e) {
                 //   e.printStackTrace();
                    isConnect = false;
                    onlineNumber--;
                    userMap.remove(userId);
                    System.out.println(userId+"号断开连接，当前剩余人数："+userMap.size());
                }
            }
        }).start();
    }

    private static Socket sendMessage(final Socket socket, String msg) {
        try{
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write(msg.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("--send message failed--");
        }
        return socket;
    }
}
