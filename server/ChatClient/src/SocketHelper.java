import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class SocketHelper {
    static class ExecuteServerInPut extends Thread implements Runnable{//接收服务器的数据
        private final Socket ToServer;

        ExecuteServerInPut(Socket ToServer){
            this.ToServer = ToServer;
        }

        @Override
        public void run() {
            try {
                Scanner scanner = new Scanner(ToServer.getInputStream());
                while (scanner.hasNext()){
                    System.out.println(scanner.nextLine());
                }
                scanner.close();
                ToServer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static class ExecuteServerOutPut extends Thread implements Runnable{//向服务器发送数据

        private final Socket socket;
        ExecuteServerOutPut(Socket Socket){
            this.socket = Socket;
        }

        @Override
        public void run() {
            try {
                PrintStream printStream = new PrintStream(socket.getOutputStream());
                Scanner scanner = new Scanner(System.in);
                while (true){
                    if(scanner.hasNextLine()) {
                        String string = scanner.nextLine();
                        printStream.println(string);
                        if (":q".equals(string)) {
                            System.out.println("退出！");
                            printStream.close();
                            scanner.close();
                            break;
                        }
                    }
                }
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
