package socket;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static socket.ChatSocket.ExecuteClientThread.findClose;

public class ChatSocket {

    private static final Map<String, Socket> clientMap = new ConcurrentHashMap<>();//存储所有的用户信息
    private static final String[] names = {"beacon", "May", "Make", "Piter"};
    private static int num = 0;

    public static void main(String[] args) {
        try {
            ExecutorService executorService = Executors.newFixedThreadPool(4);//最多容纳100个客户端聊天
            ServerSocket serverSocket = new ServerSocket(8084);
            while (num < 4) {
                Socket client = serverSocket.accept();
                num++;
                System.out.println("有新的用户连接 " + client.getInetAddress() +
                        client.getPort());
                executorService.execute(new ExecuteClientThread(client));
            }
            executorService.shutdown();
            serverSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static class ExecuteClientThread implements Runnable {
        private final Socket client;//每一个服务器线程对应一个客户端线程

        ExecuteClientThread(Socket client) {
            this.client = client;
        }

        @Override
        public void run() {
            boolean flag = true;//防止一个客户端多次注册所做的标记位置
            try {
                Scanner scanner = new Scanner(client.getInputStream());
                while (num > 0) {
                    userRegister(names[num - 1], client, flag);
                    flag = false;
                    groupChat(scanner, client);
                }
//                String str = null;//用户外部的输入信息
//                while (true) {
//                    if (scanner.hasNext()) {
//                        str = scanner.next();//外部的用户输出
//
//                        Pattern pattern = Pattern.compile("\r");//排除特殊符号
//                        Matcher matcher = pattern.matcher(str);
//                        str = matcher.replaceAll("");
//
//                        if (str.startsWith("userName")) {
//                            String userName = str.split(":")[1];
//                            userRegister(userName, client, Flag);
//                            Flag = false;
//                        }
//                        // 群聊流程
//                        else if (str.startsWith("G:")) {
//                            PrintToCilent.println("已进入群聊模式！");
//                            groupChat(scanner, client);
//                        }
//                        // 私聊流程
//                        else if (str.startsWith("P")) {//模式
//                            String userName = str.split("-")[1];
//                            PrintToCilent.println("已经进入与" + userName + "的私聊");
//
//                            privateChat(scanner, userName);
//                        }
//                        // 用户退出
//                        else if (str.contains("byebye")) {
//                            String userName = null;
//                            for (String getKey : clientMap.keySet()) {
//                                if (clientMap.get(getKey).equals(client)) {
//                                    userName = getKey;
//                                }
//                            }
//
//                            System.out.println("用户" + userName + "下线了..");
//                            clientMap.remove(userName);//将此实例从map中移除
//                        }
//                    }
//                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            while (true) {
                new Thread(() -> {
                    try {
                        Thread.sleep(3 * 1000); //设置暂停的时间 5 秒
                        findClose(clientMap.entrySet());
                        System.out.println("find close");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        }

        private void userRegister(String userName, Socket client, boolean flag) throws IOException {
            PrintStream PrintToCilent = new PrintStream(client.getOutputStream());//服务器向用户输出一些提示信息
            if (flag) {
                System.out.println("用户" + userName + "上线了！");
                clientMap.put(userName, client);//把用户加入储存map
                System.out.println("当前群聊人数为" + (clientMap.size()) + "人");
                PrintToCilent.println("注册成功！");

            } else {
                PrintToCilent.println("警告:一个客户端只能注册一个用户！");
            }
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
            while (true) {
                if (scanner.hasNextLine()) {
                    msg = scanner.nextLine();
                    if (":q".equals(msg)) {//如果用户退出了
                        sendAll(entrySet, userName, "用户" + userName + "退出了群聊");
                        System.out.println("用户" + userName + "下线了..");
                        clientMap.remove(userName);//将此实例从map中移除
                        System.out.println("当前群聊人数为" + (clientMap.size()) + "人");
                        num--;
                        return;
                    }
                    sendAll(entrySet, userName, userName + ": " + msg);
//                    for (Map.Entry<String, Socket> stringSocketEntry : entrySet) {//遍历用户的map，获取所有用户的Socket
//                        if (stringSocketEntry.getKey().equals(userName)) continue;
//                        try {
//                            Socket socket = stringSocketEntry.getValue();
//                            PrintStream ps = new PrintStream(socket.getOutputStream(), true);
//                            ps.println();//给每个用户发消息
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }

                }
            }

        }

        public static void findClose(Set<Map.Entry<String, Socket>> entrySet) {
            for (Map.Entry<String, Socket> socketEntry : entrySet) {//遍历用户的map，获取所有用户的Socket
                try {
                    socketEntry.getValue().sendUrgentData(0);
                } catch (IOException e) {
                    System.out.println("用户" + socketEntry.getKey() + "下线了..");
                    clientMap.remove(socketEntry.getKey());//将此实例从map中移除
                    System.out.println("当前群聊人数为" + (clientMap.size()) + "人");
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
        private void sendAll(Set<Map.Entry<String, Socket>> entrySet, String msg) {
            sendAll(entrySet, null, msg);
        }

        /**
         * 除了发信息的人 都发送
         *
         * @param entrySet
         * @param userName
         * @param msg
         */
        private void sendAll(Set<Map.Entry<String, Socket>> entrySet, String userName, String msg) {
            for (Map.Entry<String, Socket> socketEntry : entrySet) {//遍历用户的map，获取所有用户的Socket
                if (socketEntry.getKey().equals(userName)) {
                    continue;
                }
                findClose(entrySet);
                try {
                    Socket socket = socketEntry.getValue();
                    PrintStream ps = new PrintStream(socket.getOutputStream(), true);
                    SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
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
