package com.qqserver.service;

import com.qqcommon.Message;
import com.qqcommon.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Objects;

import static com.qqcommon.MessageType.MESSAGE_LOGIN_FAIL;
import static com.qqcommon.MessageType.MESSAGE_LOGIN_SUCCEED;

public class QQServer {
    Message message=new Message();
    private ServerSocket serverSocket=null;
    private static HashMap<String,User> validUsers=new HashMap<>();
    //在静态代码块中初始化validUsers
    static {
        validUsers.put("123",new User("123","123456"));
        validUsers.put("100",new User("100","123456"));
        validUsers.put("200",new User("200","123456"));
        validUsers.put("300",new User("300","123456"));
        validUsers.put("至尊宝",new User("至尊宝","123456"));
    }

    //写一个验证用户的方法
    public Boolean checkUser(String userId,String passwd){
        User user = validUsers.get(userId);
        if(user==null){
            return true;
        }
        if(!user.getPasswd().equals(passwd)){
            return false;
        }
        return true;
    }

    //主方法启动服务器
    public static void main(String[] args) {
        new QQServer();
    }


    //构造函数里实现服务端监听，之后创建服务端对象时会自动调用构造器
    public QQServer()  {
        try {
            serverSocket=new ServerSocket(9999);
            while (true) {
                System.out.println("正在监听");
                Socket socket = serverSocket.accept();//serversocket监听到后就创建socket
                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                User user = (User) objectInputStream.readObject();
                if (checkUser(user.getUserId(), user.getPasswd())) {
                    //验证成功
                    //准备给客户端发送带有验证成功的message
                    message.setMesType(MESSAGE_LOGIN_SUCCEED);
                    ObjectOutputStream objectOutputStream=new ObjectOutputStream(socket.getOutputStream());
                    objectOutputStream.writeObject(message);
                    System.out.println(user.getUserId() + "登录成功，可以开始传输数据");
                    //同时开启传输数据的线程
                    ServerConnectClientThread serverConnectClientThread=new ServerConnectClientThread(socket);
                    new Thread(serverConnectClientThread).start();
                    ManageServerConnectClientThread.addThread(user.getUserId(),serverConnectClientThread);

                } else {
                    //登录失败
                    message.setMesType(MESSAGE_LOGIN_FAIL);
                    ObjectOutputStream objectOutputStream=new ObjectOutputStream(socket.getOutputStream());
                    objectOutputStream.writeObject(message);
                    socket.close();
                    System.out.println(user.getUserId() + "登录失败");
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                assert serverSocket != null;
                serverSocket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
