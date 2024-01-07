package com.javaWeb;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientPoint {
    public static void main(String[] args) throws IOException {
        //连接服务器（ip，端口号），若连接上了就返回一个socket对象
        Socket socket=new Socket(InetAddress.getLocalHost(),9999);
        System.out.println("客户端发出请求，且已经和服务器连接上了，并得到一个客户端的socket");
        //创建一个socket的输出流，将一个信息写入该输入流
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write("hello,server".getBytes());//getBytes()是将一个字符串拆开成一个个字节并存放在一个数组中
        outputStream.close();
        socket.close();
        System.out.println("客户端退出。。。");
    }
}
