package com.javaWeb;

import java.io.*;
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
        BufferedInputStream bufferedInputStream=new BufferedInputStream(inputStream);
        //创建一个输出流，输出到磁盘文件中
        BufferedOutputStream bosm = new BufferedOutputStream(new FileOutputStream("D:\\shit2.jpg"));
        byte[] buff=new byte[1024];//创建一个缓存接收器
        int readlen=0;//每次输入流读一次字节流的长度
        //一边读取socket的输入流中的数据，一边把这些数据写到与本地磁盘相关的输出流管道里
        while ((readlen= bufferedInputStream.read(buff))!=-1){
            bosm.write(buff,0,readlen);
        }
        socket.shutdownInput();//socket的输入流的结束标记，让客户端知道你已经输入结束了

        //当服务端接收完图片后，再给客户端发个消息
        BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        bufferedWriter.write("服务端已收到图片");
        bufferedWriter.newLine();//表示已经写完了
        bufferedWriter.flush();//调用这个flush（），刷新一下，才能写字符进socket的输出流里去

        //逐个把资源关闭
        bufferedWriter.close();
        bosm.close();
        bufferedInputStream.close();
        socket.close();
        serverSocket.close();
        System.out.println("服务端退出");
    }
}
