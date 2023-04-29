package com.example.assignmenttwo;

import javafx.scene.paint.Paint;

public class BallConfiguration implements Configuration{
    private Paint colour;
    private double positionX;
    private double positionY;
    private double velX;
    private double velY;
    private double mass;
    private String colName;

    BallConfiguration(String colName,Paint colour,double positionX, double positionY,double velX, double velY, double mass){
        this.colName = colName;
        this.colour = colour;
        this.positionX = positionX;
        this.positionY = positionY;
        this.velX = velX;
        this.velY = velY;
        this.mass = mass;
    }

    @Override
    public double getX() {
        return positionX;
    }

    public double getY() {
        return positionY;
    }
    public Paint getColour(){
        return colour;
    }
    public double getXVel(){return velX;}
    public double getYVel(){return velY;}
    public double getMass(){return mass;}
    public double getFriction(){return 0;}
    public String getColName(){return colName;}
}
