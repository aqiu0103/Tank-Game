package com.Tank_Battles02;

public class Tank {
    private int x;
    private int y;
    private int direct;

    //坦克的移动方法
    public void MoveUp(){
        y=y-2;
    }
    public void MoveDown(){
        y=y+2;
    }
    public void MoveRight(){
        x=x+2;
    }
    public void MoveLeft(){
        x=x-2;
    }

    public void setDirect(int direct) {
        this.direct = direct;
    }

    public int getDirect() {
        return direct;
    }

    public Tank(int direct) {
        this.direct = direct;
    }

    public Tank(int x, int y){
        this.x=x;
        this.y=y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
