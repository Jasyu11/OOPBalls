package com.example.assignmenttwo;

import javafx.scene.paint.Paint;

public class TableConfiguration implements Configuration{
    private Paint colour;
    private double friction;
    private long tableX;
    private long tableY;
    private String colName;

    public TableConfiguration(Paint colour,double friction,long tableX, long tableY){
        this.colour = colour;
        this.friction = friction;
        this.tableX = tableX;
        this.tableY = tableY;
    }
    public Paint getColour(){
        return colour;
    }
    public double getX(){
        return tableX;
    }
    public double getY(){
        return tableY;
    }
    public double getFriction(){return friction;}
    public double getXVel(){return 0;}
    public double getYVel(){return 0;}
    public double getMass(){return 0;}
    public String getColName(){return colName;}


}
