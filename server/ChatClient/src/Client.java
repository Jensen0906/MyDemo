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

        new SendHelper(socket.getOutputStream(), "kehu").start();
        new ReceiveHelper(socket.getInputStream(), "client").start();
    }

    private static void sendMessage(final DataOutputStream outputStream) throws IOException{
        Scanner scanner = new Scanner(System.in);
        String msg = scanner.next();
        while (!msg.equals(":q")){
            outputStream.writeUTF(msg);
            msg = scanner.next();
        }
        scanner.close();
    }

    private static void getMessage(final DataInputStream inputStream) throws IOException {
        String msg = inputStream.readUTF();
        System.out.println("收到消息："+ msg);
    }
}
