package socket;

import javax.swing.text.html.HTML;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ChatSocket {

    private static final Map<String, Socket> clientMap = new ConcurrentHashMap<>();//存储所有的用户信息
    private static final String[] names = {"beacon", "May", "Make", "Piter"};
    private static int num = 0;

    private final static Map<String,Thread> threadPool = new HashMap<>();
    private static final SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());

    public static void main(String[] args) {
        try {
        //    ExecutorService executorService = Executors.newFixedThreadPool(4);//最多容纳100个客户端聊天

            ServerSocket serverSocket = new ServerSocket(8084);
            while (num < 4) {
                Socket client = serverSocket.accept();
                num++;
                System.out.println(format.format(new Date()) +"   有新的用户连接 " + client.getInetAddress() +
                        client.getPort());
                System.out.println("keepAlive is "+client.getKeepAlive());

                Thread thread = new ExecuteClientThread(client);
                thread.start();
                if (num == 1) {
                    new Thread(() -> {
                        System.out.println("find close");
                        while (num > 0) {
                            Set<Map.Entry<String, Socket>> entrySet =
                                    clientMap.entrySet();
                            try {
                                Thread.sleep(4000);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                         //   ExecuteClientThread.findClose(entrySet);

                        }
                        System.out.println("out find close");
                    }).start();
                }
            }
            serverSocket.close();
            System.out.println(" ---- server closed ---- ");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    static class ExecuteClientThread extends Thread implements Runnable {
        private final Socket client;//每一个服务器线程对应一个客户端线程
        ExecuteClientThread(Socket client) {
            this.client = client;
        }

        @Override
        public void run() {
            try {
                Scanner scanner = new Scanner(client.getInputStream());
                while (num > 0) {
                    userRegister(names[num - 1], client);
                    groupChat(scanner, client);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void userRegister(String userName, Socket client) throws IOException {
            PrintStream PrintToCilent = new PrintStream(client.getOutputStream());//服务器向用户输出一些提示信息
                System.out.println("user  " + userName + "  online now");
                clientMap.put(userName, client);//把用户加入储存map
                threadPool.put(userName, this);
                System.out.println("The current group chat number is " + (clientMap.size()) + " people");
                PrintToCilent.println("connect success");

        }

        private void groupChat(Scanner scanner, Socket client) throws IOException {
            Set<Map.Entry<String, Socket>> entrySet =
                    clientMap.entrySet();

            String userName = null;
            for (Map.Entry<String, Socket> socketEntry : entrySet) {//获得:是哪个用户说的话
                if (socketEntry.getValue() == client) {
                    userName = socketEntry.getKey();//发出信息的用户
                }
            }
            String msg;
            while (!this.isInterrupted()) {
                if (scanner.hasNextLine()) {
                    msg = scanner.nextLine();
                    if (":q".equals(msg)) {//如果用户退出了
                        sendAll(entrySet, userName, "user  " + userName + "  has exit chatroom");
                        System.out.println("user  " + userName + "  offline..");
                        clientMap.remove(userName);//将此实例从map中移除
                        System.out.println("The current group chat number is " + (clientMap.size()) + " people");
                        num--;
                        this.interrupt();
                        threadPool.remove(userName);
                        return;
                    }
                    System.out.println(msg + "    " +format.format(new Date()));
                    sendAll(entrySet, userName, userName + ": " + msg);

                }
            }

        }

        public static void findClose(Set<Map.Entry<String, Socket>> entrySet) {
            for (Map.Entry<String, Socket> socketEntry : entrySet) {//遍历用户的map，获取所有用户的Socket
                try {
                    socketEntry.getValue().sendUrgentData(0);
                } catch (IOException e) {

                    System.out.println("user " + socketEntry.getKey() + " is offline..");
                    clientMap.remove(socketEntry.getKey());//将此实例从map中移除
                    System.out.println("The current group chat number is " + (clientMap.size()) + " people");
                    num--;

                }
            }
        }

        /**
         * 发送全部
         *
         * @param entrySet
         * @param msg
         */
        private static void sendAll(Set<Map.Entry<String, Socket>> entrySet, String msg) {
            sendAll(entrySet, null, msg);
        }

        /**
         * 除了发信息的人 都发送
         *
         * @param entrySet
         * @param userName
         * @param msg
         */
        private static void sendAll(Set<Map.Entry<String, Socket>> entrySet, String userName, String msg) {
            for (Map.Entry<String, Socket> socketEntry : entrySet) {//遍历用户的map，获取所有用户的Socket
                if (socketEntry.getKey().equals(userName)) {
                    continue;
                }
          //      findClose(entrySet);
                try {
                    Socket socket = socketEntry.getValue();
                    PrintStream ps = new PrintStream(socket.getOutputStream(), true);
                    ps.println(format.format(new Date()) + "  " + msg);//给每个用户发消息
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}


//    private static int onlineNumber = 0;
//    private static List<Socket> userList = new ArrayList<>();
//
//    public static void main(String[] args) {
//        System.out.println("start!!!");
//        startService();
//    }
//
//    private static void startService() {
//        try {
//            ServerSocket serverSocket = new ServerSocket(8084);
//            System.out.println("get socket success, start server now");
//            while (true) {
//                System.out.println("---等待客户端连接---");
//                Socket socket = serverSocket.accept();
//                onlineNumber++;
//                userList.add(socket);
//                System.out.println(onlineNumber+"号连接，当前人数：" + userList.size());
//                DataInputStream inputStream = new DataInputStream(socket.getInputStream());
//                new Thread(() -> {
//                    startReader(inputStream);
//                }).start();
//            }
//        } catch (IOException ioException) {
//            ioException.printStackTrace();
//            System.out.println("get socket failed");
//        }
//    }
//
//    private static void startReader(final DataInputStream inputStream) {
//        try {
//            while (true) {
//                String s = inputStream.readUTF();
//                new SendHelper(s, userList, onlineNumber).start();
//                System.out.println(onlineNumber + " : " + s);
//                System.out.println("---------------------------------------------------------------------");
//            }
//        } catch (IOException e) {
//            userList.remove(--onlineNumber);
//            System.out.println((onlineNumber+1) +"号断开连接，当前剩余人数："+ userList.size());
//
//        }
//    }
