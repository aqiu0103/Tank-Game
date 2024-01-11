package com.qqserver.service;

import java.util.HashMap;

public class ManageServerConnectClientThread {
    public static HashMap<String ,ServerConnectClientThread> hashMap=new HashMap<>();

    public static void addThread(String userId,ServerConnectClientThread serverConnectClientThread){
        hashMap.put(userId, serverConnectClientThread);
    }

    public static ServerConnectClientThread getThread(String userId){
        return hashMap.get(userId);
    }
}
