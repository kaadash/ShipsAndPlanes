package controllers;

import javafx.scene.layout.Pane;
import meansOfTransport.AircraftCarrier;
import meansOfTransport.MilitaryAircraft;
import meansOfTransport.PassengerPlane;
import meansOfTransport.TravelShip;
import spawners.CivilAirport;
import spawners.Harbor;
import spawners.MilitaryAirport;
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

    private static ArrayList<PassengerPlane> passengerPlanes = new ArrayList<PassengerPlane>();
    private static ArrayList<Thread> passengerPlanesThreads = new ArrayList<Thread>();

    private static ArrayList<TravelShip> travelShips = new ArrayList<TravelShip>();
    private static ArrayList<Thread> travelShipsThreads = new ArrayList<Thread>();

    private static ArrayList<AircraftCarrier> aircraftCarriers = new ArrayList<AircraftCarrier>();

    private static ArrayList<Thread> aircraftCarriersThreads = new ArrayList<Thread>();
    private static ArrayList<MilitaryAircraft> militaryAircrafts = new ArrayList<MilitaryAircraft>();
    private static ArrayList<Thread> militaryAircraftsThreads = new ArrayList<Thread>();

    public static ArrayList<Passenger> waitingPassengers = new ArrayList<Passenger>();
    public static ArrayList<Thread> waitingPassengersThreads = new ArrayList<Thread>();

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
    public static void createNewAircraftCarrier(Pane root) {
        ArrayList<Harbor> destinationList =  Map.getHarbors();
        Collections.shuffle(destinationList);
        AircraftCarrier newAircraftCarrier= new AircraftCarrier(destinationList, root, numberOfAircraftCarrier);
        aircraftCarriers.add(newAircraftCarrier);
        aircraftCarriersThreads.add(new Thread(newAircraftCarrier));
        aircraftCarriersThreads.get(numberOfAircraftCarrier).start();
        numberOfAircraftCarrier++;
    }

    public static void createNewMilitaryAircraft(Pane root) {
        ArrayList<MilitaryAirport> destinationList = Map.getMilitaryAirports();
        Collections.shuffle(destinationList);
        int indexOfSpawner = (int)(Math.random() * aircraftCarriers.size() - 1);
        MilitaryAircraft newMilitaryAircraft= new MilitaryAircraft(destinationList, root,
                numberOfMilitaryAircraft, indexOfSpawner);

        militaryAircrafts.add(newMilitaryAircraft);
        militaryAircraftsThreads.add(new Thread(newMilitaryAircraft));
        militaryAircraftsThreads.get(numberOfMilitaryAircraft).start();
        numberOfMilitaryAircraft++;
    }

    public static void spawnPassengers(Point startingPosition) {
        int numberToSpawn = (int)(Math.random() * 100) + 20;
        while (numberToSpawn > 0) {
            Passenger newPassenger = new Passenger(startingPosition);
            waitingPassengers.add(newPassenger);
            waitingPassengersThreads.add(new Thread(newPassenger));
            waitingPassengersThreads.get(numberOfPassenger).start();
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
    public static void removeTravelShip(int ID) {
        Iterator<TravelShip> travelShipIterator = travelShips.iterator();
        int loopCounter = 0;
        while (travelShipIterator.hasNext()) {
            TravelShip p = travelShipIterator.next();
            if (p.getID()==ID) {
                travelShipIterator.remove();
                travelShipsThreads.get(loopCounter).interrupt();
            }
            loopCounter++;
        }
    }

    public static void removeAircraftCarrier(int ID) {
        Iterator<TravelShip> travelShipIterator = travelShips.iterator();
        int loopCounter = 0;
        while (travelShipIterator.hasNext()) {
            TravelShip p = travelShipIterator.next();
            if (p.getID()==ID) {
                travelShipIterator.remove();
                travelShipsThreads.get(loopCounter).interrupt();
            }
            loopCounter++;
        }
    }
    public static ArrayList<AircraftCarrier> getAircraftCarriers() {
        return aircraftCarriers;
    }
}
