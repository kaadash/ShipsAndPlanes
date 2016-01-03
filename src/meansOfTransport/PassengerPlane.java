package meansOfTransport;

import controllers.AeroplaneController;
import controllers.Dashboard;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.image.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import travelDependency.Passenger;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by kadash on 18.10.15.
 */
public class PassengerPlane extends Aeroplane {
    private int maxPassengers;

    private ArrayList<Passenger> passengersOnBoard = new ArrayList<Passenger>();

    private final String imagePath = "images/aircraft.png";

    public PassengerPlane(ArrayList<Point> allDestination, Pane context, int ID) {
        super(allDestination, context);
        this.ID = ID;
        this.imageViewPlane.setImage(new Image(imagePath));
        this.maxPassengers = 30;
    }

    public void openInformationPanel() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            TabPane root = fxmlLoader.load(getClass().getResource("aeroplaneLayout.fxml").openStream());
            AeroplaneController aeroplaneController = (AeroplaneController) fxmlLoader.getController();
            aeroplaneController.updateView(this);
            imageViewPlane.setOnMouseClicked(event -> {
                Stage stage = new Stage();
                stage.setTitle("Aeroplane Panel");
                stage.setScene(new Scene(root, 450, 450));
                stage.show();
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void animate() {
        imageViewPlane.setLayoutX(this.getCurrentPosition().getX());
        imageViewPlane.setLayoutY(this.getCurrentPosition().getY());
        openInformationPanel();
    }

    protected void checkAndAddNewPassengers(int destinationPointer) {
        int counterOfPassengersToAdd = 0;
        ArrayList<Passenger> toRemove = new ArrayList<Passenger>();
        for(Passenger passenger : Dashboard.waitingPassengers) {

//            This is the most ugly condition ever :c
            if ((passenger.getCurrentPosition().getX() == (int)Math.floor(currentPosition.getX())
                    || passenger.getCurrentPosition().getX() == (int)Math.ceil(currentPosition.getX()))
                    && (passenger.getCurrentPosition().getY() == (int)Math.floor(currentPosition.getY())
                    || passenger.getCurrentPosition().getY() == (int)Math.ceil(currentPosition.getY()))

                    && passenger.getCurrentDestination().getX() == route.get(destinationPointer).getX()
                    && passenger.getCurrentDestination().getY() == route.get(destinationPointer).getY()
                    ) {

                numberOfStaff++;
                passengersOnBoard.add(Dashboard.waitingPassengers.get(counterOfPassengersToAdd));
                toRemove.add(Dashboard.waitingPassengers.get(counterOfPassengersToAdd));
            }
            counterOfPassengersToAdd++;
        }
        Dashboard.waitingPassengers.removeAll(toRemove);
    }

    protected void checkAndAddRemovePassengers() {

        int counterOfPassengersToRemove = 0;
        ArrayList<Passenger> toRemove = new ArrayList<Passenger>();
        for(Passenger passenger : passengersOnBoard) {
//            This is the most ugly condition ever :c
            if((passenger.getCurrentDestination().getX() == (int)Math.floor(currentPosition.getX())
                    || passenger.getCurrentDestination().getX() == (int)Math.ceil(currentPosition.getX()))
                    && (passenger.getCurrentDestination().getY() == (int)Math.floor(currentPosition.getY())
                    || passenger.getCurrentDestination().getY() == (int)Math.ceil(currentPosition.getY()))) {
                passenger.changeRoute();
                numberOfStaff--;
                Dashboard.waitingPassengers.add(passenger);
                toRemove.add(passengersOnBoard.get(counterOfPassengersToRemove));
            }
            counterOfPassengersToRemove++;
        }
        passengersOnBoard.removeAll(toRemove);
    }


    @Override
    public void run() {
        int destinationPointer = 1;
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
                    if(dist < 70 && !beenIncrossRoad && dist > 2) {
                        try {
                            aeroplaneCrossRoads.acquire();
//                                Critical Section
//                            System.out.println("---- Wchodzę! ---- >>>> " + ID);
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
                                    setCurrentPosition(new Point2D.Double(x_move + getCurrentPosition().getX(),
                                            y_move + getCurrentPosition().getY()));
                                    animate();
                                    Thread.sleep(35);
                                }
                            }
                            finally {
//                                Leaving critical section
//                                System.out.println("---- Wychodzę! ---- >>>> " + ID );
                                if(destinationPointer == route.size() - 1) {
                                    destinationPointer = 0;
                                }
                                currentPosition.setLocation(this.getCurrentPosition().getX(),
                                        this.getCurrentPosition().getY());
                                currentDestination = route.get(destinationPointer);
                                destinationPointer++;
                                beenIncrossRoad = true;
                                delta_x = getCurrentDestination().getX() - getCurrentPosition().getX();
                                delta_y = getCurrentDestination().getY() - getCurrentPosition().getY();
                                goal_dist = Math.sqrt((delta_x * delta_x) + (delta_y * delta_y));
                                aeroplaneCrossRoads.release();
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
//                            this.context.getChildren().remove(imageViewPlane);
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    context.getChildren().remove(imageViewPlane);
                                }
                            });
                            return;
                        }
                    }

                } else {
                    checkAndAddNewPassengers(destinationPointer);
                    checkAndAddRemovePassengers();
                    restoreFuel();
                    beenIncrossRoad = false;
                    currentDestination = crossRoadPoint;
                    currentPosition.setLocation(this.getCurrentPosition().getX(),
                            this.getCurrentPosition().getY());
                    delta_x = getCurrentDestination().getX() - getCurrentPosition().getX();
                    delta_y = getCurrentDestination().getY() - getCurrentPosition().getY();
                    goal_dist = Math.sqrt((delta_x * delta_x) + (delta_y * delta_y));
                }
                Thread.sleep(35);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        context.getChildren().remove(imageViewPlane);
                    }
                });
                return;
            }
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    lossOfFuel();
                    animate();
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
