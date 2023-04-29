package com.example.assignmenttwo;

import javafx.scene.paint.Paint;

import java.lang.management.ThreadInfo;

public class BallBuilder implements AbstractBuilder{
    private double xPos;
    private double yPos;
    private double xVel;
    private double yVel;
    private Paint colour;
    private double mass;
    private String colName;

    public Ball build(){
        return new Ball(xPos,yPos, xVel, yVel, mass,20,colour,colName);
    }

    public void setColour(Paint colour){
        this.colour = colour;
    }
    public void setXPos(double xPos){
        this.xPos = xPos;
    }
    public void setYPos(double yPos){
        this.yPos = yPos;
    }

    public void setXVel(double xVel){
        this.xVel = xVel;
    }
    public void setYVel(double yVel){
        this.yVel = yVel;
    }
    public void setMass(double mass){
        this.mass = mass;
    }
    public void setColName(String colName){this.colName = colName;}


}
