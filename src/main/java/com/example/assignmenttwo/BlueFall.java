package com.example.assignmenttwo;

import java.util.List;

public class BlueFall implements  Fall{
    @Override
    public void fall(Ball ball,double height, double width) {
        double x,y;
        if(ball.getPocket() == 0){
            ball.setPocket(1);
            x = ball.getxStart();
            y = ball.getyStart();
            ball.setxPos(x);
            ball.setyPos(y);
            ball.setyVel(0);
            ball.setxVel(0);
        }
        else {
            ball.setPocket(2);
            ball.setyPos(height + 100);
            ball.setxPos(width + 100);
        }
    }
}
