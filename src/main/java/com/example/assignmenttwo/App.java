package com.example.assignmenttwo;

import javafx.application.Application;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws IOException {
        ConfigurationReader tableReader = new TableReader();
        Configuration tableConfiguration = tableReader.read("src/main/resources/com/example/assignmenttwo/config.json").get(0);

        GameWindow window = new GameWindow(new BallPit(tableConfiguration.getX(),tableConfiguration.getY(),tableConfiguration.getFriction(),
                tableConfiguration.getColour(),1.0/60));
        //GameWindow window = new GameWindow(new BallPit(640, 400, 1.0/60));
        window.run();

        primaryStage.setTitle("Table Balls");
        primaryStage.setScene(window.getScene());
        primaryStage.show();

        window.run();
    }


}