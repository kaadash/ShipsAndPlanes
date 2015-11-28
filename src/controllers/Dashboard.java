package controllers;

import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import meansOfTransport.AircraftCarrier;
import meansOfTransport.MilitaryAircraft;
import meansOfTransport.PassengerPlane;
import meansOfTransport.TravelShip;
import travelDependency.Passenger;

import java.awt.Point;
import java.util.ArrayList;

/**
 * Created by kadash on 18.10.15.
 */
public class Dashboard {
    private int numberOfMilitaryAircraft;
    private int numberOfPassengerPlane;
    private int numberOfAircraftCarrier;
    private int numberOfTravelShip;
    private int numberOfPassenger;

    private MilitaryAircraft[] militaryAircraftCollection;
    private static ArrayList<PassengerPlane> passengerPlanes = new ArrayList<PassengerPlane>();
    private AircraftCarrier[] aircraftCarrierCollection;
    private TravelShip[] travelShipCollection;
    private Passenger[] passengerCollection;

    public static void createNewPassengerPlane(StackPane root) {
        ArrayList<Point> destinationList =  Map.getDestinationCord(Map.getCivilAirports());
        PassengerPlane newPassengerPlane = new PassengerPlane(destinationList);
        passengerPlanes.add(newPassengerPlane);

//        newPassengerPlane.animate(
//                newPassengerPlane.getCurrentDestination(),
//                newPassengerPlane.draw(newPassengerPlane.getImagePath()));
        ImageView newPassengerPlaneImg = newPassengerPlane.draw(newPassengerPlane.getImagePath());
        newPassengerPlaneImg.setFitHeight(100);
        newPassengerPlaneImg.setFitWidth(100);
        newPassengerPlaneImg.setTranslateX(newPassengerPlane.getCurrentPosition().getX());
        newPassengerPlaneImg.setTranslateY(newPassengerPlane.getCurrentPosition().getY());

        TranslateTransition tt =
                new TranslateTransition(Duration.seconds(5), newPassengerPlaneImg);

        tt.setFromX( newPassengerPlane.getCurrentPosition().getX());
        tt.setToX( newPassengerPlane.getCurrentDestination().getX() );
        tt.setFromY( newPassengerPlane.getCurrentPosition().getX());
        tt.setToY( newPassengerPlane.getCurrentDestination().getY() );
        tt.setCycleCount( Timeline.INDEFINITE );
        tt.play();
        root.getChildren().add(newPassengerPlaneImg);
    }
    public Passenger spawnPassenger() {
        return new Passenger();
    }
}
