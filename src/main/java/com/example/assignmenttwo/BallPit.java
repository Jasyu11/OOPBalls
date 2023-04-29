package com.example.assignmenttwo;

import javafx.geometry.Point2D;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.List;

class BallPit {
    private final double height;
    private final double width;
    //private final double g;
    private final Paint colour;
    private final List<Ball> balls = new ArrayList<>();
    private long tickCount = 0;
    private long pocketCount = 0; //score
    private double friction;
    private final List<BallBuilder> ballBuilders = new ArrayList<>();
    private final List<BallDirector> ballDirectors = new ArrayList<>();
    private ConfigurationReader configurationReader = new BallReader();
    private List<Configuration> configurations;
    private List<Circle> holes = new ArrayList<>();

    BallPit(double width, double height, double friction,Paint colour, double frameDuration) {
        this.height = height;
        this.width = width;
        this.colour = colour;
        this.friction = friction;
        //g = 1.0 * frameDuration;

        configurations = configurationReader.read(
                "src/main/resources/com/example/assignmenttwo/config.json");
        for(int i=0;i<5;i++){
            ballBuilders.add(new BallBuilder());
            ballDirectors.add(new BallDirector(ballBuilders.get(i)));
            balls.add(ballDirectors.get(i).construct(configurations.get(i).getX(),configurations.get(i).getY(),
                    configurations.get(i).getXVel(),configurations.get(i).getYVel(),
                    configurations.get(i).getMass(),configurations.get(i).getColour(),configurations.get(i).getColName()));

            if(configurations.get(i).getColour().equals(Paint.valueOf("white"))){
                balls.get(i).setMoveStrategy(new WhiteMove());
                balls.get(i).setFallStrategy(new WhiteFall());
            }
            if(configurations.get(i).getColour().equals(Paint.valueOf("red"))){
                balls.get(i).setMoveStrategy(new RedMove());
                balls.get(i).setFallStrategy(new RedFall());
            }
            if(configurations.get(i).getColour().equals(Paint.valueOf("blue"))){
                balls.get(i).setMoveStrategy(new BlueMove());
                balls.get(i).setFallStrategy(new BlueFall());
            }

        }
        holes.add(0,new Circle(0,0,22));
        holes.add(1,new Circle(width/2,0,22));
        holes.add(2,new Circle(width,0,22));
        holes.add(3,new Circle(0,height,22));
        holes.add(4,new Circle(width/2,height,22));
        holes.add(5,new Circle(width,height,22));

    }
    void restart(){
        System.out.println("restart");
        ballBuilders.clear();
        ballDirectors.clear();
        balls.clear();
        configurations = configurationReader.read(
                "src/main/resources/com/example/assignmenttwo/config.json");
        for(int i=0;i<5;i++){
            ballBuilders.add(new BallBuilder());
            ballDirectors.add(new BallDirector(ballBuilders.get(i)));
            balls.add(ballDirectors.get(i).construct(configurations.get(i).getX(),configurations.get(i).getY(),
                    configurations.get(i).getXVel(),configurations.get(i).getYVel(),
                    configurations.get(i).getMass(),configurations.get(i).getColour(),configurations.get(i).getColName()));

            if(configurations.get(i).getColour().equals(Paint.valueOf("white"))){
                balls.get(i).setMoveStrategy(new WhiteMove());
                balls.get(i).setFallStrategy(new WhiteFall());
            }
            if(configurations.get(i).getColour().equals(Paint.valueOf("red"))){
                balls.get(i).setMoveStrategy(new RedMove());
                balls.get(i).setFallStrategy(new RedFall());
            }
            if(configurations.get(i).getColour().equals(Paint.valueOf("blue"))){
                balls.get(i).setMoveStrategy(new BlueMove());
                balls.get(i).setFallStrategy(new BlueFall());
            }

        }
        pocketCount = 0;
    }
    double getHeight() {
        return height;
    }

    double getWidth() {
        return width;
    }
    Paint getColour(){return colour;}

    long getPocketCount(){return pocketCount;}
    void setPocketCount(long pocketCount){
        this.pocketCount = pocketCount;

    }

    void tick() {
        tickCount++;
        if(pocketCount <5) {
            for (Ball ball : balls) {
                if (ball.getPocket() == 2) {
                    pocketCount++;
                } else if (ball.getPocket() == 3) {
                    pocketCount = -1; //white ball fall in to the pocket
                }
            }
        }
        else {

        }

        for(Ball ball: balls) {
            ball.tick();

            // Handle the edges (balls don't get a choice here)
            if (ball.getxPos() + ball.getRadius() > width) {
                    ball.setxPos(width - ball.getRadius());
                    ball.setxVel(ball.getxVel() * -1);
                //System.out.println("right");
            }
            if (ball.getxPos() - ball.getRadius() < 0) {
                ball.setxPos(0 + ball.getRadius());
                ball.setxVel(ball.getxVel() * -1);
                //System.out.println("left");
            }
            if (ball.getyPos() + ball.getRadius() > height) {
                ball.setyPos(height - ball.getRadius());
                ball.setyVel(ball.getyVel() * -1);
                //System.out.println("down");
            }
            if (ball.getyPos() - ball.getRadius() < 0) {
                ball.setyPos(0 + ball.getRadius());
                ball.setyVel(ball.getyVel() * -1);
                //System.out.println("up");
            }

            // Apply gravity if we're not on the ground (balls still don't get a choice)
            /*
            if (ball.getyPos() + ball.getRadius() < height) {
                ball.setyVel(ball.getyVel() + g);
            }*/
            for(Ball ballB: balls) {
                if (checkCollision(ball, ballB)) {  //check if the same ball
                    handleCollision(ball, ballB);
                    ball.think(holes,width,height,friction);
                    ballB.think(holes,width,height,friction);
                    checkOccupied(ball,ballB);
                }
            }

            ball.think(holes,width,height,friction);
        }
    }

    List<Ball> getBalls() {
        return balls;
    }

    private void checkOccupied(Ball ballA, Ball ballB){  //check if the same position
        if(ballA.getPocket()==1){
            if(ballA.getxPos() == ballB.getxPos() && ballA.getyPos()==ballB.getyPos()){
                ballA.setyPos(height + 100);
                ballA.setPocket(2);
            }
        }
        if(ballB.getPocket()==1){
            if(ballA.getxPos() == ballB.getxPos() && ballA.getyPos()==ballB.getyPos()){
                ballB.setyPos(height + 100);
                ballB.setPocket(2);
            }
        }
    }

    private boolean checkCollision(Ball ballA, Ball ballB) {
        if (ballA == ballB) {
            return false;
        }

        return Math.abs(ballA.getxPos() - ballB.getxPos()) < ballA.getRadius() + ballB.getRadius() &&
                Math.abs(ballA.getyPos() - ballB.getyPos()) < ballA.getRadius() + ballB.getRadius();
    }

    private void handleCollision(Ball ballA, Ball ballB) {

        //Properties of two colliding balls
        Point2D posA = new Point2D(ballA.getxPos(), ballA.getyPos());
        Point2D posB = new Point2D(ballB.getxPos(), ballB.getyPos());
        Point2D velA = new Point2D(ballA.getxVel(), ballA.getyVel());
        Point2D velB = new Point2D(ballB.getxVel(), ballB.getyVel());

        //calculate the axis of collision
        Point2D collisionVector = posB.subtract(posA);
        collisionVector = collisionVector.normalize();

        //the proportion of each balls velocity along the axis of collision
        double vA = collisionVector.dotProduct(velA);
        double vB = collisionVector.dotProduct(velB);

        //if balls are moving away from each other do nothing
        if (vA <= 0 && vB >= 0) {
            return;
        }

        // different mass
        double mR = ballB.getMass()/ballA.getMass();
        //double mR = 1;

        //The velocity of each ball after a collision can be found by solving the quadratic equation
        //given by equating momentum energy and energy before and after the collision and finding the
        //velocities that satisfy this
        //-(mR+1)x^2 2*(mR*vB+vA)x -((mR-1)*vB^2+2*vA*vB)=0
        //first we find the discriminant
        double a = -(mR + 1);
        double b = 2 * (mR * vB + vA);
        double c = -((mR - 1) * vB * vB + 2 * vA * vB);
        double discriminant = Math.sqrt(b * b - 4 * a * c);
        double root = (-b + discriminant)/(2 * a);

        //only one of the roots is the solution, the other pertains to the current velocities
        if (root - vB < 0.01) {
            root = (-b - discriminant)/(2 * a);
        }

        //The resulting changes in velocity for ball A and B
        Point2D deltaVA = collisionVector.multiply(mR * (vB - root));
        Point2D deltaVB = collisionVector.multiply(root - vB);

        ballA.setxVel(ballA.getxVel() + deltaVA.getX());
        ballA.setyVel(ballA.getyVel() + deltaVA.getY());
        ballB.setxVel(ballB.getxVel() + deltaVB.getX());
        ballB.setyVel(ballB.getyVel() + deltaVB.getY());
        //System.out.println("A "+ballA.getxVel()+" "+ballA.getyVel());
        //System.out.println("B "+ ballB.getxVel()+ " "+ ballB.getyVel());
    }
}
