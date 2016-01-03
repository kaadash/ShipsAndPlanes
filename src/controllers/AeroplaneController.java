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
import meansOfTransport.Aeroplane;
import meansOfTransport.PassengerPlane;
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
    private PassengerPlane passengerPlaneContext;

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
    @FXML
    public void reportIssue() {
        Point2D currentPosition = passengerPlaneContext.getCurrentPosition();
        double minDist = Integer.MAX_VALUE;
        Point2D minDistDestination = new Point2D.Double();
        for(Point civilAirport : Map.getDestinationCord(Map.getCivilAirports())) {
            double deltaX = civilAirport.getX() - currentPosition.getX();
            double deltaY = civilAirport.getY() - currentPosition.getY();
            double tempDist = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));
            if(minDist > tempDist) {
                minDist = tempDist;
                minDistDestination.setLocation(civilAirport.getX(), civilAirport.getY());
            }
        }
        passengerPlaneContext.setCurrentDestination(minDistDestination);
        passengerPlaneContext.setAsyncWasReportSent(true);
    }
    @FXML
    private void deletePassengerPlane(){
        Dashboard.removePassengerPlane(passengerPlaneContext.getID());
    }


    private StringProperty text = new SimpleStringProperty(this, "text", "");

    public void updateView(PassengerPlane passengerPlane) {
        passengerPlaneContext = passengerPlane;

        fuelValueLabel.setText(Integer.toString(passengerPlane.getFuel()));
        passengersValueLabel.setText(Integer.toString(passengerPlane.getPassengersOnBoard().size()) +
                "/" + passengerPlane.getMaxPassengers());
        currentPositionLabel.setText(passengerPlane.getCurrentPosition().toString());
        IDLabel.setText(Integer.toString(passengerPlane.getID()));
        currentDestinationLabel.setText(passengerPlane.getCurrentDestination().toString());
//        routeLabel.setText(passengerPlane.getRoute().toString());
        ObservableList<Passenger> data = FXCollections.observableArrayList(passengerPlane.getPassengersOnBoard());
        tableView.setItems(data);
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
    }

}
