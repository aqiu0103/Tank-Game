package com.Tank_Battles02;

import java.util.Vector;
/*
* 把射击属性放到敌方坦克属性里，由于有多个敌方坦克，每辆敌方坦克都要发射子弹，故把多个子弹发射放到vector中
* */
public class EnemyTank extends Tank implements Runnable{
    Boolean isLive=true;
    Vector<Shot> shots=new Vector<>();
    //调用父类Tank构造器
    public EnemyTank(int x,int y){
        super(x,y);
    }

    @Override
    public void run() {
        while (true){
            /*若有敌方坦克中的shots中的shot对象为空时，就重新创建一个shot对象，把该shot对象放到shots集合中，并启动该shot*/
            if(shots.isEmpty()){
                Shot shot =new Shot(getX(),getY(),getDirect());
                new Thread(shot).start();
                shots.add(shot);
            }

            switch (getDirect()){
                case 0:
                    /*让坦克先走上几步*/
                    for (int i = 0; i < 30; i++) {
                        if(getY()<=0)
                            break;
                        MoveUp();
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
                case 1:
                    for (int i = 0; i < 30; i++) {
                        if(getX()+60>=1000)
                            break;
                        MoveRight();
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
                case 2:
                    for (int i = 0; i < 30; i++) {
                        if(getY()+60>=750)
                            break;
                        MoveDown();
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
                case 3:
                    for (int i = 0; i < 30; i++) {
                        if(getX()<=0)
                            break;
                        MoveLeft();
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
            }



            setDirect((int)(Math.random()*4));  //随机改变方向

            /*若某个敌方坦克被摧毁，则让该坦克退出该线程*/
            if(!isLive){
                break;
            }
        }
    }
}
