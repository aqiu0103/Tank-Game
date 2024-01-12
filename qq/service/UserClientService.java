package com.qq.service;

import com.qq.Utils.Utility;
import com.qqcommon.Message;
import com.qqcommon.MessageType;
import com.qqcommon.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Objects;

/*
* 这个类用来实现各种服务，例如用户名和密码的验证服务
*
* */
public class UserClientService {
    private User u = new User();//该u是准备发到服务器的数据，需要给它赋值

    //验证用户名和密码是否合法
    public Boolean checkUser(String userId, String pwd) throws IOException, ClassNotFoundException {
        //给准备要发往服务端的user数据填充数据
        u.setUserId(userId);
        u.setPasswd(pwd);

        //创建socket和读写通道，并写读
        Socket socket = new Socket(InetAddress.getByName("127.0.0.1"),9999);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        objectOutputStream.writeObject(u);//给服务端发送内容
        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
        Message message = (Message) objectInputStream.readObject();//读取服务端发送过来的消息对象

        if (Objects.equals(message.getMesType(), MessageType.MESSAGE_LOGIN_SUCCEED)) {
            //若验证成功，则启动线程，保持数据能持续传输
            ClientConnectServerThread clientConnectServerThread=new ClientConnectServerThread(socket);
            new Thread(clientConnectServerThread).start();
            //为了更好管理线程，在创建一个集合来存放线程
            ManageClientConnectServerThread.addThread(u.getUserId(),clientConnectServerThread);
            return true;
        } else {
            //若验证不通过，还需要回收socket
            socket.close();
            return false;
        }
    }

    //请求拉取在线用户,客户端发送一个消息类型为请求拉取在线用户的消息给服务端
    public  void onlineFriendList(String userID){
        try {
            Message message=new Message();
            message.setMesType(MessageType.MESSAGE_GET_ONLINE_FRIEND);
            message.setSender(userID);
            ObjectOutputStream objectOutputStream=new ObjectOutputStream(ManageClientConnectServerThread.getThread(userID).getSocket().getOutputStream());
            objectOutputStream.writeObject(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //私聊功能
    public void chat(String userID){
        System.out.println("你想与谁聊天:");
        String oppoUserID = Utility.readString(20);
        System.out.println("输入内容:");
        String content = Utility.readString(50);
        Message message=new Message();
        message.setSender(userID);
        message.setContent(content);
        message.setGetter(oppoUserID);
        message.setSentTime(Utility.getTime());
        message.setMesType(MessageType.MESSAGE_CHAT);
        try {
            ObjectOutputStream objectOutputStream=new ObjectOutputStream(ManageClientConnectServerThread.getThread(userID).getSocket().getOutputStream());
            objectOutputStream.writeObject(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //正常退出客户端
    public void logout(String userID){
        Message message=new Message();
        message.setMesType(MessageType.MESSAGE_CLIENT_EXIT);
        message.setSender(userID);
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(ManageClientConnectServerThread.getThread(userID).getSocket().getOutputStream());
            objectOutputStream.writeObject(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //在管理线程中删除该线程
        ManageClientConnectServerThread.removeThread(userID);
    }
}
