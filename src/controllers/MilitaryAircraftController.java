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
import meansOfTransport.MilitaryAircraft;
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
public class MilitaryAircraftController implements Initializable {
    private MilitaryAircraft militaryAircraftContext;

    @FXML
    private Label fuelValueLabel;
    @FXML
    private Label currentPositionLabel;
    @FXML
    private Label IDLabel;
    @FXML
    private Label numberOfStaffLabel;
    @FXML
    private Label ammoTypeLabel;
    @FXML
    private Label currentDestinationLabel;
    @FXML
    public void reportIssue() {
        Point2D currentPosition = militaryAircraftContext.getCurrentPosition();
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
        militaryAircraftContext.setCurrentDestination(minDistDestination);
        militaryAircraftContext.setAsyncWasReportSent(true);
    }
    @FXML
    private void removeMilitaryAircraft(){
        Dashboard.removeMilitaryAircraft(militaryAircraftContext.getID());
    }


    private StringProperty text = new SimpleStringProperty(this, "text", "");

    public void updateView(MilitaryAircraft militaryAircraft) {
        militaryAircraftContext = militaryAircraft;

        fuelValueLabel.setText(Integer.toString(militaryAircraft.getFuel()));
        numberOfStaffLabel.setText(Integer.toString(militaryAircraft.getNumberOfStaff()));
        currentPositionLabel.setText(militaryAircraft.getCurrentPosition().toString());
        IDLabel.setText(Integer.toString(militaryAircraft.getID()));
        ammoTypeLabel.setText(militaryAircraftContext.getAmmoType());
        currentDestinationLabel.setText(militaryAircraft.getCurrentDestination().toString());
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
    }
}
