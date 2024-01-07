package com.Tank_Battles02;

public class Shot implements Runnable{
    private int x;
    private int y;
    int direct=0;
    int speed=2;
    Boolean isLive=true;

    public Shot(int x,int y,int direct){
        this.x=x;
        this.y=y;
        this.direct=direct;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public void run() {
        while(true){
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            switch (direct){
                case 0:y-=speed;
                    break;
                case 1:x+=speed;
                    break;
                case 2:y+=speed;
                    break;
                case 3:x-=speed;
                    break;
            }
            System.out.println("子弹x="+ x +"子弹y="+ y);
            if(!(x>=0&&x<=1000&&y>=0&&y<=750)){//子弹运动到边界后退出
                isLive=false;
                break;
            }
        }
    }
}
