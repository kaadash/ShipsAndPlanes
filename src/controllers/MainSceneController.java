package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by kadash on 03.01.16.
 */
public class MainSceneController implements Initializable {
    @FXML
    private Pane root;
    @FXML
    public void createPassengerPlane() {
        Dashboard.createNewPassengerPlane(this.root);
    }
    @FXML
    public void createTravelShip() {
        Dashboard.createNewTravelShip(this.root);
    }
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
    }
}
