package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import meansOfTransport.PassengerPlane;
import meansOfTransport.TravelShip;
import travelDependency.Passenger;

import java.awt.*;
import java.awt.geom.Point2D;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by kadash on 06.01.16.
 */

public class TravelShipController implements Initializable {

    private TravelShip travelShipContext;

    @FXML
    private Label companyLabel;
    @FXML
    private Label travelShipValueLabel;
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
        Point2D currentPosition = travelShipContext.getCurrentPosition();
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
        travelShipContext.setCurrentDestination(minDistDestination);
        travelShipContext.setAsyncWasReportSent(true);
    }
    @FXML
    private void removeTravelShip(){
        Dashboard.removePassengerPlane(travelShipContext.getID());
    }

    public void updateView(TravelShip travelShip) {
        travelShipContext = travelShip;

        companyLabel.setText(travelShip.getCompanyName());
        travelShipValueLabel.setText(Integer.toString(travelShip.getPassengersOnBoard().size()) +
                "/" + travelShip.getMaxPassengers());
        currentPositionLabel.setText(travelShip.getCurrentPosition().toString());
        IDLabel.setText(Integer.toString(travelShip.getID()));
        currentDestinationLabel.setText(travelShip.getCurrentDestination().toString());
//        routeLabel.setText(travelShip.getRoute().toString());
        ObservableList<Passenger> data = FXCollections.observableArrayList(travelShip.getPassengersOnBoard());
        tableView.setItems(data);
    }


    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
    }
}
