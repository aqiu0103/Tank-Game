package com.qqserver;

import java.io.Serializable;

public class Message implements Serializable {
    private static final long serialVersionUID =1L;
    private String sender;
    private String getter;
    private String content;
    private String sentTime;
    private String mesType;

    //注意这里没有构造器

    //访问和设置属性的方法
    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getGetter() {
        return getter;
    }

    public void setGetter(String getter) {
        this.getter = getter;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSentTime() {
        return sentTime;
    }

    public void setSentTime(String sentTime) {
        this.sentTime = sentTime;
    }

    public String getMesType() {
        return mesType;
    }

    public void setMesType(String mesType) {
        this.mesType = mesType;
    }
}
