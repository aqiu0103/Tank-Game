package com.javaWeb;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerPoint {
    public static void main(String[] args) throws IOException {
        //在服务端创建一个端口号为9999的serversocket
        ServerSocket serverSocket=new ServerSocket(9999);
        System.out.println("创建了一个serversocket");
        //服务端监听客户端发的请求套接字，并创建一个属于服务端的socket
        Socket socket=serverSocket.accept();
        System.out.println("与客户端建立连接并创建了一个服务端socket");
        //接下来准备接收客户端发的消息
        //先创建一个属于服务器端的socket的输入流
        InputStream inputStream = socket.getInputStream();
        byte[] buff=new byte[1024];//创建一个缓存接收器
        int readlen=0;//每次输入流读一次字节流的长度
        if((readlen= inputStream.read(buff))!=-1){
            System.out.println(new String(buff,0,readlen));
        }
        inputStream.close();
        socket.close();
        serverSocket.close();
        System.out.println("服务端退出");
    }
}
