package main;

import controllers.Dashboard;
import controllers.Map;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.*;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;
import meansOfTransport.MilitaryAircraft;
import meansOfTransport.PassengerPlane;

import java.awt.*;
import java.util.ArrayList;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        StackPane root = FXMLLoader.load(getClass().getResource("sample.fxml"));
//        root.setId("bg");
//        primaryStage.getIcons().add(new Image("images/Fall.png"));
        Map.prepareMap(root);
        Button test = new Button("adsdasd");


        root.getChildren().add(test);
        Dashboard.createNewPassengerPlane(root);
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        primaryStage.setMaximized(true);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
