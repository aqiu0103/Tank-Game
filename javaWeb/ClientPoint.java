package com.javaWeb;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Objects;

public class ClientPoint {
    public static void main(String[] args) throws IOException {
        //连接服务器（ip，端口号），若连接上了就返回一个socket对象
        Socket socket=new Socket(InetAddress.getLocalHost(),9999);
        System.out.println("客户端发出请求，且已经和服务器连接上了，并得到一个客户端的socket");
        //创建一个socket的输出流”管道“
        OutputStream outputStream = socket.getOutputStream();
        BufferedOutputStream bufferedOutputStream=new BufferedOutputStream(outputStream);
        //然后读取图片到内存中
        BufferedInputStream bism = new BufferedInputStream(new FileInputStream("D:\\shit.jpg"));
        byte[] buff = new byte[1024];
        int readlen=0;
        //一边读，一边传到socket的输出流里
        while ((readlen= bism.read(buff))!=-1){
            bufferedOutputStream.write(buff,0,readlen);
        }
        socket.shutdownOutput();//socket的输出流的结束标记，让服务端知道你已经输出结束了

        //再确认一下服务端是否已经收到图片
        BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
        if(Objects.equals(bufferedReader.readLine(), "服务端已收到图片")){
            System.out.println("服务端确实已经收到图片了");
        }
        socket.shutdownInput();

        //逐个关闭
        bufferedReader.close();
       // bufferedOutputStream.close();
        bism.close();
        socket.close();
        System.out.println("客户端退出。。。");
    }
}
