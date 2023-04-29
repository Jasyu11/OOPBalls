package com.example.assignmenttwo;

import javafx.scene.paint.Paint;

public class BallDirector {
    private AbstractBuilder builder;

    public BallDirector(AbstractBuilder builder){
        this.builder = builder;
    }
    public Ball construct(double xPos, double yPos, double xVel, double yVel, double mass, Paint colour,String colName){
        builder.setColour(colour);
        builder.setXPos(xPos);
        builder.setYPos(yPos);
        builder.setXVel(xVel);
        builder.setYVel(yVel);
        builder.setMass(mass);
        builder.setColName(colName);
        return builder.build();
    }
}
