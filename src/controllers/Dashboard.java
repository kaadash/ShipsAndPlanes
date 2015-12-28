package controllers;

import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
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
    private static ArrayList<Thread> passengerPlanesThreads = new ArrayList<Thread>();
    private AircraftCarrier[] aircraftCarrierCollection;
    private TravelShip[] travelShipCollection;
    private Passenger[] passengerCollection;
    private static int loop = 0;

    public static void createNewPassengerPlane(Pane root) {
        ArrayList<Point> destinationList =  Map.getDestinationCord(Map.getCivilAirports());
        PassengerPlane newPassengerPlane = new PassengerPlane(destinationList, root);
        passengerPlanes.add(newPassengerPlane);
//        newPassengerPlane.run();
        passengerPlanesThreads.add(new Thread(newPassengerPlane));
        passengerPlanesThreads.get(loop).start();
        loop++;

////        DoubleProperty xValue = new SimpleDoubleProperty();
//
//        double xValue = newPassengerPlaneImg.translateXProperty().doubleValue();
//        double translateConst = sizeImage/2 + newPassengerPlane.getCurrentPosition().getX();
//
//        newPassengerPlane.lossOfFuel();
//        if(translateConst  == newPassengerPlane.getCurrentDestination().getX()) {
//            newPassengerPlane.getRoute().add(newPassengerPlane.getRoute().get(0));
//            newPassengerPlane.getRoute().remove(0);
//            Point destination = newPassengerPlane.getRoute().get(1);
//            Point currentPosition = newPassengerPlane.getRoute().get(0);
//            newPassengerPlane.setCurrentDestination(destination);
//            newPassengerPlane.setCurrentPosition(currentPosition );
//            newPassengerPlane.animate(newPassengerPlaneImg);
//            newPassengerPlane.setFuel(1000);
//        }
//        root.getChildren().add(newPassengerPlaneImg);
    }
    public Passenger spawnPassenger() {
        return new Passenger();
    }
}
