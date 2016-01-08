package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import meansOfTransport.AircraftCarrier;

import java.awt.*;
import java.awt.geom.Point2D;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by kadash on 07.01.16.
 */
public class AircraftCarrierController implements Initializable {
    private AircraftCarrier aircraftCarrierContext;

    @FXML
    private Label ammoTypeLabel;
    @FXML
    private Label currentPositionLabel;
    @FXML
    private Label IDLabel;
    @FXML
    public void reportIssue() {
        Point2D currentPosition = aircraftCarrierContext.getCurrentPosition();
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
        aircraftCarrierContext.setCurrentDestination(minDistDestination);
        aircraftCarrierContext.setAsyncWasReportSent(true);
    }
    @FXML
    public void removeAircraftCarrier(){
        Dashboard.removeAircraftCarrier(aircraftCarrierContext.getID());
    }
    public void updateView(AircraftCarrier aircraftCarrier) {
        aircraftCarrierContext = aircraftCarrier;

        ammoTypeLabel.setText(aircraftCarrier.getAmmoType());
        currentPositionLabel.setText(aircraftCarrier.getCurrentPosition().toString());
        IDLabel.setText(Integer.toString(aircraftCarrier.getID()));
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
    }
}
