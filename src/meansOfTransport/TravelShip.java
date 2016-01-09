package meansOfTransport;

import controllers.Dashboard;
import controllers.TravelShipController;
import interfaces.Transporter;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
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
        super(allDestination, context);
        this.imageViewMeanOfTransport.setImage(new Image(imagePath));
        for (Harbor harbor : allDestination) {
            this.route.add(harbor);
        }
        this.ID = id;
        this.maxPassengers = 20;
        this.companyName = generateCompanyName();
        this.currentPosition = this.route.get(0).getRightLaneStartingPoint();
        this.currentDestination = this.route.get(0).getRightLaneEndingPoint();
        imageViewMeanOfTransport.setFitWidth(40);
        imageViewMeanOfTransport.setFitHeight(40);
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

        double delta_x = getCurrentDestination().getX() - getCurrentPosition().getX();
        double delta_y = getCurrentDestination().getY() - getCurrentPosition().getY();
        double goal_dist = Math.sqrt((delta_x * delta_x) + (delta_y * delta_y));

        while (true) {
            try {
                double speed_per_tick = 2;
                double currentDelta_x = getCurrentDestination().getX() - getCurrentPosition().getX();
                double currentDelta_Y = getCurrentDestination().getY() - getCurrentPosition().getY();
                double dist = Math.sqrt((currentDelta_x * currentDelta_x) + (currentDelta_Y * currentDelta_Y));

                if(asyncWasReportSent) {
                    delta_x = getCurrentDestination().getX() - getCurrentPosition().getX();
                    delta_y = getCurrentDestination().getY() - getCurrentPosition().getY();
                    goal_dist = Math.sqrt((delta_x * delta_x) + (delta_y * delta_y));
                    beenIncrossRoad = true;
                    asyncWasReportSent = false;
                }

                if (Math.floor(dist) != 0) {
                    double ratio = speed_per_tick / goal_dist;
                    double x_move = ratio * delta_x;
                    double y_move = ratio * delta_y;
                    setCurrentPosition(new Point2D.Double(x_move + getCurrentPosition().getX(),
                            y_move + getCurrentPosition().getY()));

                    if(dist < 70 && !beenIncrossRoad && dist > 2 ) {
                        try {
                            aeroplaneCrossRoads.acquire();
//                                Critical Section
                            try {
                                while(Math.floor(dist) >= 1){
                                    if(asyncWasReportSent) {
                                        delta_x = getCurrentDestination().getX() - getCurrentPosition().getX();
                                        delta_y = getCurrentDestination().getY() - getCurrentPosition().getY();
                                        goal_dist = Math.sqrt((delta_x * delta_x) + (delta_y * delta_y));
                                        ratio = speed_per_tick / goal_dist;
                                        x_move = ratio * delta_x;
                                        y_move = ratio * delta_y;
                                        aeroplaneCrossRoads.release();
                                        asyncWasReportSent = false;
                                    }
                                    currentDelta_x = getCurrentDestination().getX() - getCurrentPosition().getX();
                                    currentDelta_Y = getCurrentDestination().getY() - getCurrentPosition().getY();
                                    dist = Math.sqrt((currentDelta_x * currentDelta_x) + (currentDelta_Y * currentDelta_Y));

                                    if((Math.floor(dist) < 4) && travelCounter == 0) {
                                        currentDestination = route.get(destinationPointer).getLeftLaneEndingPoint();
                                        delta_x = getCurrentDestination().getX() - getCurrentPosition().getX();
                                        delta_y = getCurrentDestination().getY() - getCurrentPosition().getY();
                                        goal_dist = Math.sqrt((delta_x * delta_x) + (delta_y * delta_y));
                                        ratio = speed_per_tick / goal_dist;
                                        x_move = ratio * delta_x;
                                        y_move = ratio * delta_y;
                                        travelCounter++;
                                    }

                                    setCurrentPosition(new Point2D.Double(x_move + getCurrentPosition().getX(),
                                            y_move + getCurrentPosition().getY()));

                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            animate();
                                            openInformationPanel();
                                        }
                                    });
                                    Thread.sleep(60);
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
                                delta_x = getCurrentDestination().getX() - getCurrentPosition().getX();
                                delta_y = getCurrentDestination().getY() - getCurrentPosition().getY();
                                goal_dist = Math.sqrt((delta_x * delta_x) + (delta_y * delta_y));
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    context.getChildren().remove(imageViewMeanOfTransport);
                                }
                            });
                            return;
                        }
                    }

                } else {
                    travelCounter++;
                    switch (travelCounter) {
                        case 3:
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
                    delta_x = getCurrentDestination().getX() - getCurrentPosition().getX();
                    delta_y = getCurrentDestination().getY() - getCurrentPosition().getY();
                    goal_dist = Math.sqrt((delta_x * delta_x) + (delta_y * delta_y));
                }
                Thread.sleep(60);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        context.getChildren().remove(imageViewMeanOfTransport);
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
                passengersOnBoard.add(Dashboard.waitingPassengers.get(counterOfPassengersToAdd));
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
