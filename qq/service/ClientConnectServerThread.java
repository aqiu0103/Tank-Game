package com.qq.service;

import com.qq.common.Message;

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

    public Socket getSocket() {
        return socket;
    }
    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    @Override
    //一直循环读写数据
    public void run() {
        while (true) {
            try {
                //这个线程一直搁着运行，服务端一有消息发过来，客户端就能接收
                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                Message message= (Message) objectInputStream.readObject();
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
