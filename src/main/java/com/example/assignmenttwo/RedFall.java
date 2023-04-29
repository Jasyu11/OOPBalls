package com.example.assignmenttwo;

public class RedFall implements Fall{
    @Override
    public void fall(Ball ball,double height, double width) {
        ball.setPocket(2);
        ball.setyPos(height + 100);  //out of window
        ball.setxPos(width + 100);
    }
}
