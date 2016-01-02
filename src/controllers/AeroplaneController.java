package controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import travelDependency.Passenger;

import java.awt.*;
import java.awt.geom.Point2D;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by kadash on 29.12.15.
 */
public class AeroplaneController implements Initializable {
    @FXML
    private Label fuelValueLabel;
    @FXML
    private Label passengersValueLabel;
    @FXML
    private Label currentPositionLabel;
    @FXML
    private Label IDLabel;
    @FXML
    private Label currentDestinationLabel;
    @FXML
    private Label routeLabel;
    @FXML
    private TableView<Passenger> tableView;

    private StringProperty text = new SimpleStringProperty(this, "text", "");

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
    }

    public void updateView(int fuel, int passengersValue, Point2D currentPosition,
                           int ID, Point2D currentDestination, ArrayList<Point> route,
                           ArrayList<Passenger> passengers) {

        fuelValueLabel.setText(Integer.toString(fuel));
        passengersValueLabel.setText(Integer.toString(passengersValue));
        currentPositionLabel.setText(currentPosition.toString());
        IDLabel.setText(Integer.toString(ID));
        currentDestinationLabel.setText(currentDestination.toString());
        routeLabel.setText(route.toString());
        ObservableList<Passenger> data = FXCollections.observableArrayList(passengers);
        tableView.setItems(data);
    }

}
