package com.qqserver.service;

import com.qqcommon.Message;
import com.qqcommon.MessageType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerConnectClientThread implements Runnable{
    private Socket socket;

    //构造函数
    public ServerConnectClientThread(Socket socket) {
        this.socket = socket;
    }

    public Socket getSocket(){
        return socket;
    }

    @Override
    public void run() {
        while (true) {
            try {
                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                Message message = (Message) objectInputStream.readObject();
                //看看客户端有没有发送什么请求过来
                if (message.getMesType().equals(MessageType.MESSAGE_GET_ONLINE_FRIEND)) {//若客户端请求拉取在线用户列表
                    System.out.println(message.getSender() + "请求拉取在线用户列表");
                    Message message1 = new Message();
                    message1.setMesType(MessageType.MESSAGE_RET_ONLINE_FRIEND);
                    message1.setContent(ManageServerConnectClientThread.getOnlineUsersList());
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                    objectOutputStream.writeObject(message1);
                } else if (message.getMesType().equals(MessageType.MESSAGE_CLIENT_EXIT)) {//若客户端请求退出
                    //先关socket
                    ManageServerConnectClientThread.getThread(message.getSender()).getSocket().close();//或者直接socket.close()就行
                    //在ManageServerConnectClientThread中去除该线程
                    ManageServerConnectClientThread.removeThread(message.getSender());
                    System.out.println(message.getSender() + "已退出");
                    break;
                } else if (message.getMesType().equals(MessageType.MESSAGE_CHAT)) {//若客户端请求私聊
                    ObjectOutputStream objectOutputStream=new ObjectOutputStream(ManageServerConnectClientThread.getThread(message.getGetter()).getSocket().getOutputStream());
                    Message message1=new Message();
                    message1.setSender(message.getSender());
                    message1.setGetter(message.getGetter());
                    message1.setContent(message.getContent());
                    message1.setSentTime(message.getSentTime());
                    message1.setMesType(MessageType.MESSAGE_CHAT);
                    objectOutputStream.writeObject(message1);
                }
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
