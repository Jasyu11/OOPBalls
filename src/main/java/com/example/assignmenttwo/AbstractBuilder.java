package com.example.assignmenttwo;

import javafx.scene.paint.Paint;

public interface AbstractBuilder {
    public void setColour(Paint colour);
    public void setXPos(double xPos);
    public void setYPos(double yPos);
    public void setXVel(double xVel);
    public void setYVel(double yVel);
    public void setMass(double mass);
    public void setColName(String colName);
    public Ball build();
}
