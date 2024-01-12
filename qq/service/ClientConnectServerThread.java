package com.qq.service;

import com.qqcommon.Message;
import com.qqcommon.MessageType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ClientConnectServerThread implements Runnable{
    //该客户端连接服务端的用于持续传输数据的线程需要的属性有socket
    Socket socket;

    //构造器
    public ClientConnectServerThread(Socket socket) {
        this.socket = socket;
    }


    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    //返回该线程持有的socket
    public Socket getSocket() {
        return socket;
    }

    @Override
    //一直循环读写数据
    public void run() {
        while (true) {
            try {
                //这个线程一直搁着运行，服务端一有消息发过来，客户端就能接收
                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                Message message= (Message) objectInputStream.readObject();
                //先判断服务端发的消息的类型
                if(message.getMesType().equals(MessageType.MESSAGE_RET_ONLINE_FRIEND)){//若服务端发过来的消息类型是在线用户列表
                    //由于读写线程在登录成功后就启动了，故这里可直接输出消息内容
                    System.out.println(message.getContent());
                } else if (message.getMesType().equals(MessageType.MESSAGE_CHAT)) {
                    System.out.println(message.getSentTime() + "  " + message.getSender() + "对" + message.getGetter() + ":" +message.getContent());
                } else {
                    System.out.println("稍后处理");
                }
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
