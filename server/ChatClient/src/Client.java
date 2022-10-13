import entity.HeartBeatPack;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Client {

    public static void main(String[] args) throws IOException {

        Socket socket = new Socket("124.221.10.82", 8084);
        System.out.println("keepAlive is "+ socket.getKeepAlive());
        Thread in = new SocketHelper.ExecuteServerInPut(socket);
        Thread out = new SocketHelper.ExecuteServerOutPut(socket);
        in.start();
        out.start();
        new KeepSocketAlive(socket).start();
    }

    static class KeepSocketAlive extends Thread implements Runnable {

        private final Socket socket;

        KeepSocketAlive(Socket socket) {
            this.socket = socket;
        }
        @Override
        public void run() {
            int i = 0;
            while (!this.isInterrupted()) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    new Throwable("sleep error").printStackTrace();
                }
                try {
                    socket.sendUrgentData(0);
                } catch (IOException e) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
                    System.out.println("socket has been closed    " + dateFormat.format(new Date()));
                    this.interrupt();
                    System.exit(0);
                }

            }
        }
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
