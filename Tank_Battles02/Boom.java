package com.Tank_Battles02;

public class Boom {
    int x;
    int y;
    int life=9;//记录爆炸生命周期
    Boolean isLive=true;

    public Boom(int x, int y) {//构造器
        this.x = x;
        this.y = y;
    }

    //生命减少方法
    public void lifeDown(){
        if(life>0){
            life--;
        }
        else isLive=false;
    }
}
