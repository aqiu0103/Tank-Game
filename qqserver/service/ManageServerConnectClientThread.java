package com.qqserver.service;

import java.util.HashMap;
import java.util.Iterator;

public class ManageServerConnectClientThread {
    public static HashMap<String ,ServerConnectClientThread> hashMap=new HashMap<>();

    public static void addThread(String userId,ServerConnectClientThread serverConnectClientThread){
        hashMap.put(userId, serverConnectClientThread);
    }

    public static ServerConnectClientThread getThread(String userId){
        return hashMap.get(userId);
    }

    //移除线程方法
    public static void removeThread(String userID){
        hashMap.remove(userID);
    }

    //返回在线用户列表
    public static String getOnlineUsersList(){
        String onlineuserlist="";
        Iterator<String> iterator=hashMap.keySet().iterator();
        while (iterator.hasNext()){
            onlineuserlist += iterator.next().toString() + " ";
        }
        return onlineuserlist;
    }

}

