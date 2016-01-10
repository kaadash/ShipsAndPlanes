package meansOfTransport;

import controllers.AeroplaneController;
import controllers.Dashboard;
import helpers.MutableDouble;
import interfaces.Transporter;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TabPane;
import javafx.scene.image.*;
import javafx.scene.layout.Pane;
import spawners.Airport;
import spawners.CivilAirport;
import travelDependency.Passenger;

import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.ArrayList;

public class PassengerPlane extends Aeroplane implements Transporter {
    private int maxPassengers;

    private ArrayList<Passenger> passengersOnBoard = new ArrayList<Passenger>();

    private final String imagePath = "images/aircraft.png";

    /**
     * Set up destination by civilAirport position ArrayList and shuffle them
     * set up id and randomize all fields
     * @param allDestination
     * @param context
     * @param ID
     */
    public PassengerPlane(ArrayList<CivilAirport> allDestination, Pane context, int ID) {
        super(context);
        for (Airport airport : allDestination) {
            this.route.add(airport);
        }
        this.currentPosition = this.route.get(0).getRightLaneStartingPoint();
        this.currentDestination = this.route.get(0).getRightLaneEndingPoint();

        this.ID = ID;
        this.imageViewOfObject.setImage(new Image(imagePath));
        this.maxPassengers = 30;
    }

    /**
     * Send information about changes in model to controller
     */
    public void openInformationPanel() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            TabPane root = fxmlLoader.load(getClass().getResource("aeroplaneLayout.fxml").openStream());
            AeroplaneController aeroplaneController = (AeroplaneController) fxmlLoader.getController();
            aeroplaneController.updateView(this);
            addClickActionToObject(root, "Passenger Plane Dashboard");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Check if there are any passengers with the same position and current desitnation
     * Remove from waiting passengers and add to passengers on board
     * @param destinationPointer
     */
    @Override
    public void checkAndAddNewPassengers(int destinationPointer) {
        int counterOfPassengersToAdd = 0;
        ArrayList<Passenger> toRemove = new ArrayList<Passenger>();
        for(Passenger passenger : Dashboard.waitingPassengers) {

//            This is the most ugly condition ever :c
            if ((passenger.getCurrentPosition().getX() == (int)Math.floor(currentPosition.getX())
                    || passenger.getCurrentPosition().getX() == (int)Math.ceil(currentPosition.getX()))
                    && (passenger.getCurrentPosition().getY() == (int)Math.floor(currentPosition.getY())
                    || passenger.getCurrentPosition().getY() == (int)Math.ceil(currentPosition.getY()))

                    && passenger.getCurrentDestination().getX() == route.get(destinationPointer + 1).getLeftLaneStartingPoint().getX()
                    && passenger.getCurrentDestination().getY() == route.get(destinationPointer + 1).getLeftLaneStartingPoint().getY()
                    ) {
                if(passengersOnBoard.size() < maxPassengers) {
                    passengersOnBoard.add(Dashboard.waitingPassengers.get(counterOfPassengersToAdd));
                }
                toRemove.add(Dashboard.waitingPassengers.get(counterOfPassengersToAdd));
            }
            counterOfPassengersToAdd++;
        }
        Dashboard.waitingPassengers.removeAll(toRemove);
    }

    /**
     * Remove from passengers on board and add to waiting passengers
     *
     * */
    @Override
    public void checkAndAddRemovePassengers() {
        int counterOfPassengersToRemove = 0;
        ArrayList<Passenger> toRemove = new ArrayList<Passenger>();
        for(Passenger passenger : passengersOnBoard) {
//            This is the most ugly condition ever :c
            if((passenger.getCurrentDestination().getX() == (int)Math.floor(currentPosition.getX())
                    || passenger.getCurrentDestination().getX() == (int)Math.ceil(currentPosition.getX()))
                    && (passenger.getCurrentDestination().getY() == (int)Math.floor(currentPosition.getY())
                    || passenger.getCurrentDestination().getY() == (int)Math.ceil(currentPosition.getY()))) {
                passenger.changeRoute();
                Dashboard.waitingPassengers.add(passenger);
                toRemove.add(passengersOnBoard.get(counterOfPassengersToRemove));
                passenger.setOnDestinationPoint(true);
            }
            counterOfPassengersToRemove++;
        }
        passengersOnBoard.removeAll(toRemove);
    }

    /**
     * Main logic about running passenger plane
     */
    @Override
    public void run() {
        int destinationPointer = 1;
        int travelCounter = 0;
//        Check if in starting city, there are passengers wanting to travel
        checkAndAddNewPassengers(destinationPointer);

        MutableDouble deltaX = new MutableDouble();
        MutableDouble deltaY = new MutableDouble();
        MutableDouble goalDist = new MutableDouble();
        updatePositionOnMap(deltaX, deltaY, goalDist);

        while (true) {
            try {
                int speedPerTick = 2;
                MutableDouble currentDeltaX = new MutableDouble();
                MutableDouble currentDeltaY = new MutableDouble();
                MutableDouble dist = new MutableDouble();
                updateCurrentCordinates(currentDeltaX, currentDeltaY, dist);
                if(asyncWasReportSent) {
                    updatePositionOnMap(deltaX, deltaY, goalDist);
                    beenIncrossRoad = true;
                    asyncWasReportSent = false;
                }

                if (Math.floor(dist.getValue()) != 0) {
                    MutableDouble ratio = new MutableDouble();
                    MutableDouble xMove = new MutableDouble();
                    MutableDouble yMove = new MutableDouble();
                    updateStepToMove(ratio, deltaX, deltaY, xMove, yMove, goalDist, speedPerTick);
                    setCurrentPosition(new Point2D.Double(xMove.getValue() + getCurrentPosition().getX(),
                            yMove.getValue() + getCurrentPosition().getY()));

                    if(dist.getValue() < 110 && !beenIncrossRoad && dist.getValue() > 2 ) {
                        try {
                            aeroplaneCrossRoads.acquire();
//                                Critical Section
                            try {
                                while(Math.floor(dist.getValue()) >= 1){
                                    if(asyncWasReportSent) {
                                        updatePositionOnMap(deltaX, deltaY, goalDist);
                                        updateStepToMove(ratio, deltaX, deltaY, xMove, yMove, goalDist, speedPerTick);
                                        aeroplaneCrossRoads.release();
                                        asyncWasReportSent = false;
                                    }
                                    updateCurrentCordinates(currentDeltaX, currentDeltaY, dist);

                                    if((Math.floor(dist.getValue()) < 4) && travelCounter == 0) {
                                        currentDestination = route.get(destinationPointer).getLeftLaneEndingPoint();
                                        updatePositionOnMap(deltaX, deltaY, goalDist);
                                        updateStepToMove(ratio, deltaX, deltaY, xMove, yMove, goalDist, speedPerTick);
                                        travelCounter++;
                                    }

                                    setCurrentPosition(new Point2D.Double(xMove.getValue() + getCurrentPosition().getX(),
                                            yMove.getValue() + getCurrentPosition().getY()));

                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            animate();
                                            openInformationPanel();
                                        }
                                    });
                                    Thread.sleep(35);
                                }
                            }
                            finally {
//                                Leaving critical section
                                if(destinationPointer == route.size() - 1) {
                                    destinationPointer = 0;
                                }
                                currentPosition.setLocation(this.getCurrentPosition().getX(),
                                        this.getCurrentPosition().getY());
                                destinationPointer++;
                                travelCounter++;
                                if (travelCounter == 2) {
                                    currentDestination = route.get(destinationPointer).getLeftLaneStartingPoint();
                                    beenIncrossRoad = true;
                                    aeroplaneCrossRoads.release();
                                }
                                updatePositionOnMap(deltaX, deltaY, goalDist);
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    context.getChildren().remove(imageViewOfObject);
                                }
                            });
                            return;
                        }
                    }
                }
                else {
                    travelCounter++;
                    switch (travelCounter) {
                        case 3:
                            int baseValue = route.get(destinationPointer).getCurrentVehicles();
                            route.get(destinationPointer).setCurrentVehicles(baseValue + 1);
                            Thread.sleep(3000);
                            checkAndAddRemovePassengers();
                            currentDestination = route.get(destinationPointer).getRightLaneStartingPoint();
                            restoreFuel();
                            break;
                        case 4:
                            baseValue = route.get(destinationPointer).getCurrentVehicles();
                            route.get(destinationPointer).setCurrentVehicles(baseValue - 1);
                            checkAndAddNewPassengers(destinationPointer);
                            currentDestination = route.get(destinationPointer).getRightLaneEndingPoint();
                            beenIncrossRoad = false;
                            travelCounter = 0;
                            break;
                        default:
                            System.out.println("error");
                            break;
                    }
                    currentPosition.setLocation(this.getCurrentPosition().getX(),
                            this.getCurrentPosition().getY());
                    updatePositionOnMap(deltaX, deltaY, goalDist);
                }
                Thread.sleep(35);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        context.getChildren().remove(imageViewOfObject);
                    }
                });
                return;
            }
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    lossOfFuel();
                    animate();
                    openInformationPanel();
                }
            });
        }
    }

    public ArrayList<Passenger> getPassengersOnBoard() {
        return passengersOnBoard;
    }

    public void setPassengersOnBoard(ArrayList<Passenger> passengersOnBoard) {
        this.passengersOnBoard = passengersOnBoard;
    }

    public int getMaxPassengers() {
        return maxPassengers;
    }

    public void setMaxPassengers(int maxPassengers) {
        this.maxPassengers = maxPassengers;
    }
}
