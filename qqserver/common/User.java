package com.qqserver;

import java.io.Serializable;

public class User implements Serializable {
    private static final long serialVersionUID =1L;
    private  String userId;
    private  String passwd;

    //构造器
    public User(String userId, String passwd) {
        this.userId = userId;
        this.passwd = passwd;
    }

    //访问和设置属性的方法

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }
}
