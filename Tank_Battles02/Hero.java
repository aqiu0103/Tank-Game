package com.Tank_Battles02;

import java.util.Vector;

public class Hero extends Tank {
    Vector<Shot> shots=new Vector<>();
    Boolean isLive=true;

    public Hero(int x,int y){
        super(x,y);
    }


    /*现在已经有shot线程了，写一个方法让hero坦克去启动shot线程，并且根据hero的方向来确定shot线程的初始位置*/
    public void shotEnemyTank(){
        switch (getDirect()){//调用父类方法getDirect()得到hero的方向
            case 0:
                Shot shot1=new Shot(getX()+30,getY(),getDirect());//创建方向为0的shot线程实体
                shots.add(shot1);//把该shot线程放到shots集合中
                new Thread(shot1).start();//启动shot线程
                break;
            case 1:
                Shot shot2=new Shot(getX()+60,getY()+30,getDirect());
                shots.add(shot2);
                new Thread(shot2).start();
                break;
            case 2:
                Shot shot3=new Shot(getX()+30,getY()+60,getDirect());
                shots.add(shot3);
                new Thread(shot3).start();
                break;
            case 3:
                Shot shot4=new Shot(getX(),getY()+30,getDirect());
                shots.add(shot4);
                new Thread(shot4).start();
                break;
        }
    }
}
