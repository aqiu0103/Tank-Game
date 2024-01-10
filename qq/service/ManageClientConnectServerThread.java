package com.qq.service;

import java.util.HashMap;

public class ManageClientConnectServerThread {
    //创建一个静态哈希表来存放线程，让用户名作为索引，方便以后寻找用户的线程
    private static HashMap<String,ClientConnectServerThread> hashMap=new HashMap<>();

    //写一个添加线程的静态方法,参数为用户名和线程
    public static void addThread(String userId,ClientConnectServerThread clientConnectServerThread){
        hashMap.put(userId,clientConnectServerThread);
    }

    //写一个根据用户名得到线程的静态方法
    public static ClientConnectServerThread getThread(String userId){
        return hashMap.get(userId);
    }
}
