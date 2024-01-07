package com.Tank_Battles02;

import javax.swing.*;

public class TankGame extends JFrame {
    //属性
    MyPanel mp=null;
    public static void main(String[] args) {
        new TankGame();
    }
    //构造器
    public TankGame(){
        mp=new MyPanel();
        new Thread(mp).start();
        this.add(mp);
        this.addKeyListener(mp);
        this.setSize(1000,750);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}
