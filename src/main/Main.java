package main;

import controllers.Dashboard;
import controllers.Map;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;



public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Pane root = FXMLLoader.load(getClass().getResource("sample.fxml"));
//        root.setId("bg");
        primaryStage.getIcons().add(new Image("images/Fall.png"));
        Map.prepareMap(root);
        Button btn = new Button("create new passenger plane");
        root.getChildren().add(btn);
        btn.setOnAction(event-> {
            Dashboard.createNewPassengerPlane(root);
        });



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
