package meansOfTransport;

import controllers.Dashboard;
import controllers.TravelShipController;
import helpers.MutableDouble;
import interfaces.Transporter;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import spawners.Harbor;
import travelDependency.Passenger;

import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.ArrayList;

public class TravelShip extends Ship implements Transporter {
    private String companyName;

    private final String imagePath = "images/ship.png";

    private int maxPassengers;

    private boolean beenIncrossRoad;

    private ArrayList<Passenger> passengersOnBoard = new ArrayList<Passenger>();

    public TravelShip(ArrayList<Harbor> allDestination, Pane context, int id ) {
        super(context);
        this.imageViewOfObject.setImage(new Image(imagePath));
        for (Harbor harbor : allDestination) {
            this.route.add(harbor);
        }
        this.ID = id;
        this.maxPassengers = 20;
        this.companyName = generateCompanyName();
        this.currentPosition = this.route.get(0).getRightLaneStartingPoint();
        this.currentDestination = this.route.get(0).getRightLaneEndingPoint();
        imageViewOfObject.setFitWidth(40);
        imageViewOfObject.setFitHeight(40);
    }

    private String generateCompanyName() {
        String companyName[] = {"Super", "Fine", "Hello", "FaceBook", "AreYouSure"};
        return companyName[(int)(Math.random() * companyName.length - 1)];
    }

    public void openInformationPanel() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            TabPane root = fxmlLoader.load(getClass().getResource("travelShipLayout.fxml").openStream());
            TravelShipController travelShipController = (TravelShipController) fxmlLoader.getController();
            travelShipController.updateView(this);
            addClickActionToObject(root, "Travel Ship Dashboard");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
                                    Thread.sleep(maxVelocity);
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

                } else {
                    travelCounter++;
                    switch (travelCounter) {
                        case 3:
                            Thread.sleep(3000);
                            checkAndAddRemovePassengers();
                            currentDestination = route.get(destinationPointer).getRightLaneStartingPoint();
                            break;
                        case 4:
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
                Thread.sleep(maxVelocity);
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
                    animate();
                    openInformationPanel();
                }
            });
        }
    }

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
// TODO: fix out of bound index
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
            }
            counterOfPassengersToRemove++;
        }
        passengersOnBoard.removeAll(toRemove);
    }


    public String getCompanyName() {
        return companyName;
    }
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    public int getMaxPassengers() {
        return maxPassengers;
    }

    public void setMaxPassengers(int maxPassengers) {
        this.maxPassengers = maxPassengers;
    }

    public ArrayList<Passenger> getPassengersOnBoard() {
        return passengersOnBoard;
    }

    public void setPassengersOnBoard(ArrayList<Passenger> passengersOnBoard) {
        this.passengersOnBoard = passengersOnBoard;
    }
}
