package meansOfTransport;

import controllers.AeroplaneController;
import controllers.Dashboard;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.image.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import travelDependency.Passenger;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.Semaphore;

public abstract class Aeroplane extends MeansOfTransport{
    protected int numberOfStaff;

    protected int fuel;
    private ArrayList<Passenger> passengersOnBoard = new ArrayList<Passenger>();

    protected ImageView imageViewPlane = new ImageView(new Image("images/aircraft.png"));

    public static Semaphore aeroplaneCrossRoads = new Semaphore(1);

    public Aeroplane(ArrayList<Point> allDestination, Pane context) {
        super(allDestination, context);
        for (int i = 0; i < allDestination.size(); i++) {
            this.route.add(allDestination.get(i));
        }
        Collections.shuffle(this.route);
        this.crossRoadPoint = new Point(550, 350);
        this.currentDestination = this.crossRoadPoint;
        this.currentPosition = this.route.get(0);
        this.fuel = 1000;
        int sizeImage = 50;
        imageViewPlane.setFitHeight(sizeImage);
        imageViewPlane.setFitWidth(sizeImage);
        context.getChildren().add(imageViewPlane);
    }

    public void lossOfFuel() {
        this.fuel--;
    }

    public Point findNearestAirport() {
        Point nearestAirport = new Point();
        nearestAirport.x = 12;
        nearestAirport.y = 12;
        return nearestAirport;
    }

    public ImageView draw(String imagePath) {
        Image image = new Image(imagePath);
        return new ImageView(image);
    }

    public void openInformationPanel() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            TabPane root = fxmlLoader.load(getClass().getResource("aeroplaneLayout.fxml").openStream());
            AeroplaneController aeroplaneController = (AeroplaneController) fxmlLoader.getController();
            aeroplaneController.updateView(fuel, numberOfStaff, currentPosition, ID, currentDestination, route, passengersOnBoard);
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

    public void reportIssue() {

    }

    public void landOnNearestAirport() {

    }

    public void restoreFuel() {

    }

    public void animate() {
        imageViewPlane.setLayoutX(this.getCurrentPosition().getX());
        imageViewPlane.setLayoutY(this.getCurrentPosition().getY());
        openInformationPanel();
    }

    @Override
    public void run() {
        int destinationPointer = 0;
        boolean beenIncrossRoad = false;
        double delta_x = getCurrentDestination().getX() - getCurrentPosition().getX();
        double delta_y = getCurrentDestination().getY() - getCurrentPosition().getY();
        double goal_dist = Math.sqrt((delta_x * delta_x) + (delta_y * delta_y));

        while (true) {
            try {
                double speed_per_tick = 2;
                double currentDelta_x = getCurrentDestination().getX() - getCurrentPosition().getX();
                double currentDelta_Y = getCurrentDestination().getY() - getCurrentPosition().getY();
                double dist = Math.sqrt((currentDelta_x * currentDelta_x) + (currentDelta_Y * currentDelta_Y));
//                System.out.println(dist);

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
                                    currentDelta_x = getCurrentDestination().getX() - getCurrentPosition().getX();
                                    currentDelta_Y = getCurrentDestination().getY() - getCurrentPosition().getY();
                                    dist = Math.sqrt((currentDelta_x * currentDelta_x) + (currentDelta_Y * currentDelta_Y));
                                    setCurrentPosition(new Point2D.Double(x_move + getCurrentPosition().getX(),
                                            y_move + getCurrentPosition().getY()));
                                    animate();
//                                    System.out.println("bla bla " + dist);
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
                        }
                    }

                } else {
                    currentDestination = crossRoadPoint;
                    beenIncrossRoad = false;
                    checkAndAddNewPassengers();
                    checkAndAddRemovePassengers();

//                    imageViewPlane.setLayoutX(this.getCurrentPosition().getX());

                    currentPosition.setLocation(this.getCurrentPosition().getX(),
                            this.getCurrentPosition().getY());
                    delta_x = getCurrentDestination().getX() - getCurrentPosition().getX();
                    delta_y = getCurrentDestination().getY() - getCurrentPosition().getY();
                    goal_dist = Math.sqrt((delta_x * delta_x) + (delta_y * delta_y));
                }
                Thread.sleep(35);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    animate();
                }
            });
        }
    }
    private void checkAndAddNewPassengers() {
        int counterOfPassengersToAdd = 0;
        ArrayList<Passenger> toRemove = new ArrayList<Passenger>();
        for(Passenger passenger : Dashboard.waitingPassengers) {
            if ((passenger.getCurrentPosition().getX() == (int)Math.floor(currentPosition.getX())
                    || passenger.getCurrentPosition().getX() == (int)Math.ceil(currentPosition.getX()))
                    && (passenger.getCurrentPosition().getY() == (int)Math.floor(currentPosition.getY())
                    || passenger.getCurrentPosition().getY() == (int)Math.ceil(currentPosition.getY()))) {

                numberOfStaff++;
                passengersOnBoard.add(Dashboard.waitingPassengers.get(counterOfPassengersToAdd));
                toRemove.add(Dashboard.waitingPassengers.get(counterOfPassengersToAdd));
                System.out.println("Dodano " + numberOfStaff);
                System.out.println(passenger.getCurrentPosition());
                System.out.println(currentPosition);
            }
            counterOfPassengersToAdd++;
        }
        Dashboard.waitingPassengers.removeAll(toRemove);
    }

    private void checkAndAddRemovePassengers() {

        int counterOfPassengersToRemove = 0;
        ArrayList<Passenger> toRemove = new ArrayList<Passenger>();
        for(Passenger passenger : passengersOnBoard) {
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

    public int getFuel() {
        return fuel;
    }

    public void setFuel(int fuel) {
        this.fuel = fuel;
    }
}