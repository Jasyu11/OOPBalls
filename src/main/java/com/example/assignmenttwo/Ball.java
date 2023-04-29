package com.example.assignmenttwo;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

import java.util.List;
import java.util.Random;

public class Ball {
    private double xPos;
    private final double xStart;
    private double yPos;
    private final double yStart;
    private final double radius;
    private double xVel;
    private double yVel;
    private final Paint colour;
    private final double mass;
    private double pocket = 0; //0-null，1-once, 2-twice fall
    private final String colName;
    private Move moveStrategy;
    private Fall fallStrategy;

    Ball(double startX, double startY, double xVel, double yVel, double mass, double startRadius, Paint colour,String colName) {
        this.xPos = startX;
        this.yPos = startY;
        this.xStart = startX;
        this.yStart = startY;
        this.radius = startRadius;
        this.colour = colour;
        this.xVel = xVel;
        this.yVel = yVel;
        this.mass = mass;
        this.colName = colName;
        //System.out.println(xVel+ " v " + yVel +", " + xPos +" pos "+ yPos);
        //xVel = new Random().nextInt(5);
        //yVel = new Random().nextInt(5);
    }

    public void setMoveStrategy(Move strategy){
        this.moveStrategy = strategy;
    }
    public void setFallStrategy(Fall strategy){this.fallStrategy = strategy;}

    void tick() {
        //System.out.println(xVel+" vTICK " + yVel);
        xPos += xVel;
        yPos += yVel;
    }
    double getxStart(){return xStart;}
    double getyStart(){return yStart;}

    void setxVel(double xVel) {
        this.xVel = xVel;
    }

    void setyVel(double yVel) {
        this.yVel = yVel;
    }

    double getRadius() {
        return radius;
    }

    double getxPos() {
        return xPos;
    }

    double getyPos() {
        return yPos;
    }

    Paint getColour() {
        return colour;
    }

    double getxVel() {
        return xVel;
    }

    double getyVel() {
        return yVel;
    }

    void setxPos(double xPos) {
        this.xPos = xPos;
    }

    void setyPos(double yPos) {
        this.yPos = yPos;
    }
    double getMass(){return mass;}

    void  setPocket(double pocket){
        this.pocket = pocket;
    }
    double getPocket(){return pocket;}


    void think(List<Circle> holes,double width, double height, double friction) {

        for(Circle hole:holes){
            if(hole.contains(xPos,yPos)){
                fallStrategy.fall(this,width,height);
            }
        }
        if(pocket != 2){
            moveStrategy.move(this,width,height,friction);

        }

    }
}
