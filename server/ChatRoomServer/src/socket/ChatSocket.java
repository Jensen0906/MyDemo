package socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.http.WebSocket;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class ChatSocket {

    private static int onlineNumber = 0;

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
                System.out.println("连接：" + socket);
                startReader(socket);
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
            System.out.println("get socket failed");
        }
    }

    private static void startReader(final Socket socket) {
      //  sendMessage(socket, "-- Hello, This is server --");
        new Thread(() -> {
            DataInputStream reader;
            try {
                // 获取读取流
                reader = new DataInputStream(socket.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
                InputStream inputStream = socket.getInputStream();
                System.out.println("*等待客户端输入*");
                while (reader.readBoolean()) {
                   // reader.readUTF();
                    System.out.println(reader.available());
                    // 读取数据
                    byte[] bytes = new byte[8];
                    reader.read(bytes);
                    String msg = new String(bytes);
                    System.out.println("获取到客户端的信息：" + msg);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private static Socket sendMessage(final Socket socket, String msg) {
        try{
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write(msg.getBytes(StandardCharsets.UTF_8));
         //   outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("--send message failed--");
        }
        return socket;
    }
}
