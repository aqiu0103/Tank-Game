package com.qq.QQView;

import com.qq.Utils.Utility;
import com.qq.service.UserClientService;

import java.io.IOException;

public class QQView {
    private Boolean loop=true;//控制是否显示主菜单。默认一直显示
    private String key="";//接收用户的键盘输入
    private UserClientService userClientService=new UserClientService();//创建一个qq客户端服务对象

    //主方法
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        new QQView().mainMenu();//创建对象并调用显示主菜单方法
        System.out.println("=========退出客户端系统成功=========");
        System.exit(0);//退出系统
    }

    //显示主菜单方法
    private void mainMenu() throws IOException, ClassNotFoundException {
        while (loop){
            System.out.println("=========欢迎登录我们的网络通信系统=========");
            System.out.println("\t\t 1 登录系统");
            System.out.println("\t\t 9 退出系统");
            System.out.print("输入你的选择:");

            key= Utility.readString(1);//输入一个字符

            //根据用户的输入来处理不同的逻辑
            switch (key){
                case "1":
                    System.out.print("请输入用户名:");
                    String userId=Utility.readString(50);
                    System.out.print("请输入密码:");
                    String passwd=Utility.readString(50);

                    //这里需要去服务端验证用户名和密码是否合法，这里有很多代码，先暂时设为true
                    if(userClientService.checkUser(userId,passwd)){
                        System.out.println("=========欢迎"+ userId +"=========");
                        //这里就是二级菜单了
                        while (loop){
                            System.out.println("\n=========网络通信系统二级菜单（"+ userId+ "）=========");
                            System.out.println("\t\t 1 显示在线用户列表");
                            System.out.println("\t\t 2 群发消息");
                            System.out.println("\t\t 3 私聊消息");
                            System.out.println("\t\t 4 发送文件");
                            System.out.println("\t\t 9 退出系统");
                            System.out.println("请输入您的选择:");
                            key=Utility.readString(1);
                            switch (key){
                                case "1":
                                    System.out.println("显示用户列表");
                                    userClientService.onlineFriendList(userId);//发送请求给服务端
                                    break;
                                case "2":
                                    System.out.println("群发消息");
                                    break;
                                case "3":
                                    System.out.println("私聊消息");
                                    userClientService.chat(userId);
                                    break;
                                case "4":
                                    System.out.println("发送文件");
                                    break;
                                case "9":
                                    userClientService.logout(userId);
                                    loop=false;
                                    break;
                            }
                        }
                    }else {
                        System.out.println("========用户名或密码错误，请重新输入========");
                    }
                    break;
                case "9":
                    loop=false;
                    break;
            }

        }
    }

}
