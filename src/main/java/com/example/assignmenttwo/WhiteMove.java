package com.example.assignmenttwo;

public class WhiteMove implements Move{
    public void move(Ball ball, double width, double height,double friction){
        double xV = ball.getxVel();
        double yV = ball.getyVel();

        if(xV <= 0.1 && xV >= -0.1){
            xV = 0;
        }
        else if(xV != 0.0){
            if(xV > 0.0)
                xV = xV - friction * 0.07;
            else
                xV = xV + friction * 0.07;
        }
        if(yV <= 0.1 && yV >= -0.1){
            yV = 0;
        }
        else if(yV != 0.0 ){
            if(yV > 0.0)
                yV =yV- friction * 0.07;
            else
                yV =yV + friction * 0.07;
        }
        ball.setxVel(xV);
        ball.setyVel(yV);
    }
}
