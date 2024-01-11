package com.qqserver.service;

import com.qqcommon.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ServerConnectClientThread implements Runnable{
    private Socket socket;

    //构造函数
    public ServerConnectClientThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            ObjectInputStream objectInputStream=new ObjectInputStream(socket.getInputStream());
            Message message=(Message) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}
