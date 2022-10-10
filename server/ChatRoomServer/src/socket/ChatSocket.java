package socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatSocket {

    private static int onlineNumber = 0;
    private static List<Socket> userList = new ArrayList<>();

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
                userList.add(socket);
                System.out.println(onlineNumber+"号连接，当前人数：" + userList.size());
                new ReceiveHelper(socket.getInputStream(), userList, onlineNumber).start();
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
            while (isConnect) {
                try {
                    reader = new DataInputStream(socket.getInputStream());
                    String msg = reader.readUTF();
                    System.out.println(userId +"号：" + msg);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            sendMessageAll(userId, msg);
                        }
                        
                    }).start();
                } catch (IOException e) {
                 //   e.printStackTrace();
                    isConnect = false;
                    onlineNumber--;
                    userList.remove(userId);
                    System.out.println(userId+"号断开连接，当前剩余人数："+ userList.size());
                }
            }
        }).start();
    }

    private static void sendMessageAll(int userId, String msg) {
        System.out.println("sendAll");
        try{
            for (int i=0; i < userList.size(); i++) {
                if(i == userId-1) {
                    continue;
                }
                Socket user = userList.get(i);
                DataOutputStream outputStream = new DataOutputStream(user.getOutputStream());
                outputStream.writeUTF(msg);
                System.out.println(String.format("发送给%s号---%s", i+1, msg));
                outputStream.close();
            }
        } catch (IOException e) {
          //  e.printStackTrace();
            System.out.println("--send message failed--");
        }
    }
}
