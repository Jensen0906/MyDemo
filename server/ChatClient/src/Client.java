import java.io.IOException;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException {

        Socket socket = new Socket("127.0.0.1", 8084);
        new SocketHelper.ExecuteServerInPut(socket).start();
        new SocketHelper.ExecuteServerOutPut(socket).start();
    }
//    private static boolean close = false;
//
//    public static void main(String[] args) throws IOException {
//        Socket socket;
//        try {
//            socket = new Socket("127.0.0.1", 8084);
//        } catch (Exception exception) {
//            System.out.println("connect failed");
//            return;
//        }
//        final DataInputStream inputStream = new DataInputStream(socket.getInputStream());
//        final DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
//        new Thread(() -> sendMessage(outputStream)).start();
//        new Thread(() -> {
//            getMessage(inputStream);
//        }).start();
//    }
//
//    private static void sendMessage(final DataOutputStream outputStream){
//        Scanner scanner = new Scanner(System.in);
//        String msg = "";
//        while (scanner.hasNextLine()){
//            msg = scanner.nextLine();
//            if (msg.equals(":q")) {
//                break;
//            }
//            try {
//                outputStream.writeUTF(msg);
//            } catch (IOException e) {
//                System.out.println("send failed");
//            }
//        }
//        System.out.println("-----close socket-----");
//        scanner.close();
//    }
//
//    private static void getMessage(final DataInputStream inputStream) {
//        try {
//            String msg = inputStream.readUTF();
//            System.out.println("收到消息："+ msg);
//            System.out.println("----------------------------------------------------");
//        } catch (IOException e) {
//            System.out.println("receive failed");
//        }
//    }
}
