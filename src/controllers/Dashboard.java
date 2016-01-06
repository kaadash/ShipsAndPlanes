package controllers;

import javafx.scene.layout.Pane;
import meansOfTransport.AircraftCarrier;
import meansOfTransport.MilitaryAircraft;
import meansOfTransport.PassengerPlane;
import meansOfTransport.TravelShip;
import spawners.CivilAirport;
import spawners.Harbor;
import travelDependency.Passenger;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

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

    private static ArrayList<TravelShip> travelShips = new ArrayList<TravelShip>();
    private static ArrayList<Thread> travelShipsThreads = new ArrayList<Thread>();

    private AircraftCarrier[] aircraftCarrierCollection;
    public static ArrayList<Passenger> waitingPassengers = new ArrayList<Passenger>();

    public static void createNewPassengerPlane(Pane root) {
        ArrayList<CivilAirport> destinationList =  Map.getCivilAirports();
        Collections.shuffle(destinationList);
        spawnPassengers(destinationList.get(0).getRightLaneStartingPoint());
        PassengerPlane newPassengerPlane = new PassengerPlane(destinationList, root, numberOfPassengerPlane);
        passengerPlanes.add(newPassengerPlane);
        passengerPlanesThreads.add(new Thread(newPassengerPlane));
        passengerPlanesThreads.get(numberOfPassengerPlane).start();
        numberOfPassengerPlane++;
    }
    public static void createNewTravelShip(Pane root) {
        ArrayList<Harbor> destinationList =  Map.getHarbors();
        Collections.shuffle(destinationList);
        spawnPassengers(destinationList.get(0).getRightLaneStartingPoint());
        TravelShip newTravelShip= new TravelShip(destinationList, root, numberOfTravelShip);
        travelShips.add(newTravelShip);
        travelShipsThreads.add(new Thread(newTravelShip));
        travelShipsThreads.get(numberOfTravelShip).start();
        numberOfTravelShip++;
    }


    public static void spawnPassengers(Point startingPosition) {
        int numberToSpawn = (int)(Math.random() * 100) + 20;
        while (numberToSpawn > 0) {
            waitingPassengers.add(new Passenger(startingPosition));
            numberToSpawn--;
            numberOfPassenger++;
        }
    }
    public static void removePassengerPlane(int ID) {
        Iterator<PassengerPlane> passengerPlaneIterator = passengerPlanes.iterator();
        int loopCounter = 0;
        while (passengerPlaneIterator.hasNext()) {
            PassengerPlane p = passengerPlaneIterator.next();
            if (p.getID()==ID) {
                passengerPlaneIterator.remove();
                passengerPlanesThreads.get(loopCounter).interrupt();
            }
            loopCounter++;
        }
    }
}
