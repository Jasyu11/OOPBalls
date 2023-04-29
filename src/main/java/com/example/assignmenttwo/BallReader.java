package com.example.assignmenttwo;

import javafx.scene.paint.Paint;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BallReader implements ConfigurationReader{

    public List<Configuration> read(String path){
        List<Configuration> ballConfigurations = new ArrayList<>();
        JSONParser parser = new JSONParser();
        try {
            Object object = parser.parse(new FileReader(path));

            // convert Object to JSONObject
            JSONObject jsonObject = (JSONObject) object;

            // reading the "Balls" section:
            JSONObject jsonBalls = (JSONObject) jsonObject.get("Balls");

            // reading the "Balls: ball" array:
            JSONArray jsonBallsBall = (JSONArray) jsonBalls.get("ball");

            // reading from the array:
            for (Object obj : jsonBallsBall) {
                JSONObject jsonBall = (JSONObject) obj;

                // the ball colour is a String
                String colour = (String) jsonBall.get("colour");

                // the ball position, velocity, mass are all doubles
                Double positionX = (Double) ((JSONObject) jsonBall.get("position")).get("x");
                Double positionY = (Double) ((JSONObject) jsonBall.get("position")).get("y");

                Double velocityX = (Double) ((JSONObject) jsonBall.get("velocity")).get("x");
                Double velocityY = (Double) ((JSONObject) jsonBall.get("velocity")).get("y");

                Double mass = (Double) jsonBall.get("mass");
                ballConfigurations.add(new BallConfiguration(colour,Paint.valueOf(colour),positionX,positionY,velocityX,velocityY,mass));
                //System.out.println("Ball x: " + positionX +velocityX+ ", mass: " + mass);
            }
            return ballConfigurations;
            //return new TableConfiguration(Paint.valueOf(tableColour),tableFriction,tableX,tableY);
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }
        return null;
        //return new BallConfiguration();
    }
}
