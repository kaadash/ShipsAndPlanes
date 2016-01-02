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
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by kadash on 18.10.15.
 */
public class Dashboard {
    private static int numberOfMilitaryAircraft = 0;
    private static int numberOfPassengerPlane = 0;
    private static int numberOfAircraftCarrier = 0;
    private static int numberOfTravelShip = 0;
    private static int numberOfPassenger = 0;

    private MilitaryAircraft[] militaryAircraftCollection;
    private static ArrayList<PassengerPlane> passengerPlanes = new ArrayList<PassengerPlane>();
    private static ArrayList<Thread> passengerPlanesThreads = new ArrayList<Thread>();
    private AircraftCarrier[] aircraftCarrierCollection;
    private TravelShip[] travelShipCollection;
    public static ArrayList<Passenger> waitingPassengers = new ArrayList<Passenger>();

    public static void createNewPassengerPlane(Pane root) {
        ArrayList<Point> destinationList =  Map.getDestinationCord(Map.getCivilAirports());
        PassengerPlane newPassengerPlane = new PassengerPlane(destinationList, root, numberOfPassengerPlane);
        passengerPlanes.add(newPassengerPlane);
        passengerPlanesThreads.add(new Thread(newPassengerPlane));
        passengerPlanesThreads.get(numberOfPassengerPlane).start();
        numberOfPassengerPlane++;
        spawnPassengers();
    }

    public static void spawnPassengers() {
        int numberToSpawn = (int)(Math.random() * 20) + 20;
        while (numberToSpawn > 0) {
            waitingPassengers.add(new Passenger());
            numberToSpawn--;
            numberOfPassenger++;
        }
    }
}
