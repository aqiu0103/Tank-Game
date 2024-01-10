package com.qq.service;

import com.qq.common.Message;
import com.qq.common.MessageType;
import com.qq.common.User;

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
}
