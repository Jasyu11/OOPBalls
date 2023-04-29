package com.example.assignmenttwo;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.awt.*;
import java.awt.event.MouseListener;
import java.beans.EventHandler;
import java.nio.Buffer;

class GameWindow {
    private final GraphicsContext gc;
    private Scene scene;
    private BallPit model;
    private boolean haveDragged = false;
    private boolean haveReleased = false;
    private boolean clicked = false;
    private double startX;
    private double startY;
    private double endX;
    private double endY;
    private double whiteX;
    private double whiteY;
    private boolean whiteStop = false;
    private int stopTime =0;
    private Pane pane;


    GameWindow(BallPit model) {
        this.model = model;
        pane = new Pane();
        this.scene = new Scene(pane, model.getWidth(), model.getHeight());
        Canvas canvas = new Canvas(model.getWidth(), model.getHeight());
        gc = canvas.getGraphicsContext2D();
        pane.getChildren().add(canvas);
        //System.out.println("create node");
    }

    Scene getScene() {
        return this.scene;
    }

    void run() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(17),
                t -> this.draw()));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private double calculateV(double start, double end){
        double V;
        V = (start - end) * 0.1;
        return V;
    }
    private void draw() {
        model.tick();
        gc.clearRect(0, 0, model.getWidth(), model.getHeight());
        gc.setFill(model.getColour());  //set colour pf background
        gc.fillRect(0, 0, model.getWidth(), model.getHeight());
        gc.setFill(Paint.valueOf("white"));
        gc.setFont(Font.font(30));
        gc.fillText(new StringBuilder().append("Score: ").append(model.getPocketCount()).toString(),30,30);
        gc.setFill(Paint.valueOf("grey"));
        gc.fillOval(-22, -22, 44, 44);
        gc.fillOval(-22,model.getHeight()-22,44,44);
        gc.fillOval(model.getWidth()/2-22,-22,44,44);
        gc.fillOval(model.getWidth()/2-22, model.getHeight()-22,44,44);
        gc.fillOval(model.getWidth()-22,-22,44,44);
        gc.fillOval(model.getWidth()-22, model.getHeight()-22,44,44);


        if(model.getPocketCount() >= 4 ) {
            gc.setFill(Paint.valueOf("white"));
            gc.fillText("Win and bye",100,100);
        }
        else if(model.getPocketCount() == -1){
            gc.setFill(Paint.valueOf("white"));
            gc.fillRect(100,100,50,20);
            gc.setFill(Paint.valueOf("black"));
            gc.fillText("Restart",100,100);
            Rectangle rectangle = new Rectangle(100,100,50,20);
            scene.setOnMouseClicked(event -> {
                if(rectangle.contains((int) event.getX(), (int) event.getY())){
                    model.restart();
                    model.setPocketCount(0);
                }
            });
            //model.restart();
        }
        else {
            gc.setFill(Paint.valueOf("white"));
            gc.fillText("Please drag the white cue ball",30,50);
            for (Ball ball : model.getBalls()) {
                if(ball.getColour().equals(Paint.valueOf("WHITE"))){
                    //System.out.println("white");
                    if(ball.getxVel() == 0 && ball.getyVel()==0){
                        whiteX = ball.getxPos();
                        whiteY = ball.getyPos();
                        whiteStop = true;
                    }
                    else {
                        whiteStop = false;
                    }
                }
                gc.setFill(ball.getColour());
                gc.fillOval(ball.getxPos() - ball.getRadius(),
                        ball.getyPos() - ball.getRadius(),
                        ball.getRadius() * 2,
                        ball.getRadius() * 2);

            }

            if(whiteStop == true){
                stopTime++;
                Circle circle = new Circle(whiteX,whiteY,20);
                if(haveDragged == false){
                    scene.setOnMousePressed(event -> {
                        if(circle.contains(event.getX(),event.getY())) {
                            startX = event.getX();
                            startY = event.getY();
                            //System.out.println("pressed");
                            haveDragged = true;
                            gc.setFill(Paint.valueOf("black"));
                            gc.fillText("Clicked the white ball",0,20);
                        }
                        else {
                            //System.out.println("out of white circle");
                            gc.setFill(Paint.valueOf("black"));
                            gc.fillText("Please click the white ball",0,50);
                        }
                    });
            }
                if (haveDragged == true){
                    scene.setOnMouseReleased(event -> {
                        endX = event.getX();
                        endY = event.getY();
                        System.out.println("Released");
                        haveReleased = true;
                    });
                }
                if(haveDragged == true && haveReleased == true){
                    System.out.println("dragged");
                    haveDragged = false;
                    haveReleased = false;
                    clicked = true;
                }
                if(clicked == true){
                    gc.setFill(Paint.valueOf("black"));
                    gc.setLineWidth(10);
                    gc.strokeLine(startX,startY,endX,endY);
                    //System.out.println("clicked");
                    if(stopTime>=120){
                        clicked =false;
                        for(Ball ball: model.getBalls()){
                            if(ball.getColour().equals(Paint.valueOf("WHITE"))){
                                ball.setxVel(calculateV(startX,endX));
                                ball.setyVel(calculateV(startY,endY));
                                System.out.println("start rolling");
                            }
                        }
                        stopTime =0;

                    }
                }

            }



        }
    }
}
