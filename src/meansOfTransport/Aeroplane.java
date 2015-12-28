package meansOfTransport;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.Semaphore;

public class Aeroplane extends MeansOfTransport {
    protected int numberOfStaff;

    protected int fuel;

    private ImageView imageViewPlane = new ImageView(new Image("images/aircraft.png"));

    public static Semaphore aeroplaneCrossRoads = new Semaphore(1);

    public Aeroplane(ArrayList<Point> allDestination, Pane context) {
        super(allDestination, context);
        for (int i = 0; i < allDestination.size(); i++) {
            this.route.add(allDestination.get(i));
        }
        Collections.shuffle(this.route);
        this.crossRoadPoint = new Point(550, 350);
//        this.currentDestination = this.route.get(1);
        this.currentDestination = this.crossRoadPoint;
        this.currentPosition = this.route.get(0);
        this.fuel = 1000;
        this.ID = (int)(Math.random() * 20);
        int sizeImage = 50;
        openInformationPanel();
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
        imageViewPlane.setOnMouseClicked(event -> {
            Label fuelLabel = new Label(Integer.toString(this.getFuel()));
            fuelLabel.setTranslateX(50);
            Label currentDestinationLabel = new Label();
            Label currentPositionLabel = new Label(currentPosition.toString());
            currentDestinationLabel.setTranslateX(150);
            currentDestinationLabel.setTranslateY(100);
            currentPositionLabel.setTranslateX(150);
            currentPositionLabel.setTranslateY(50);

            GridPane root;
            try {
                root = FXMLLoader.load(getClass().getResource("aeroplaneLayout.fxml"));
                root.getChildren().add(fuelLabel);
                root.getChildren().add(currentDestinationLabel);
                root.getChildren().add(currentPositionLabel);
                currentDestinationLabel.setText(currentDestination.toString());
                Stage stage = new Stage();
                stage.setTitle("Aeroplane Panel");
                stage.setScene(new Scene(root, 450, 450));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void reportIssue() {

    }

    public void landOnNearestAirport() {

    }

    public void restoreFuel() {

    }

    public int getFuel() {
        return fuel;
    }

    public void setFuel(int fuel) {
        this.fuel = fuel;
    }

    public void animate() {
        imageViewPlane.setLayoutX(this.getCurrentPosition().getX());
        imageViewPlane.setLayoutY(this.getCurrentPosition().getY());
    }

    @Override
    public void run() {
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
                System.out.println(dist);

                if (Math.floor(dist) != 0) {
                    double ratio = speed_per_tick / goal_dist;
                    double x_move = ratio * delta_x;
                    double y_move = ratio * delta_y;
                    setCurrentPosition(new Point2D.Double(x_move + getCurrentPosition().getX(),
                            y_move + getCurrentPosition().getY()));
                    if(dist < 70 && !beenIncrossRoad && dist > 2) {
                        try {
                            aeroplaneCrossRoads.acquire();
                            System.out.println("---- Wchodzę! ---- >>>> " + ID);
                            try {
                                while(Math.floor(dist) >= 1){
                                    currentDelta_x = getCurrentDestination().getX() - getCurrentPosition().getX();
                                    currentDelta_Y = getCurrentDestination().getY() - getCurrentPosition().getY();
                                    dist = Math.sqrt((currentDelta_x * currentDelta_x) + (currentDelta_Y * currentDelta_Y));
                                    setCurrentPosition(new Point2D.Double(x_move + getCurrentPosition().getX(),
                                            y_move + getCurrentPosition().getY()));
                                    animate();
                                    System.out.println("bla bla " + dist);
                                    Thread.sleep(35);
                                }
                            }
                            finally {
                                System.out.println("---- Wychodzę! ---- >>>> " + ID );
                                currentDestination = route.get((int)(Math.random() * 9));
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
//                    System.out.println(x_move + " " + delta_x + " " + y_move + " " + delta_y);
//                    System.out.println(dist);
//                    System.out.println(getCurrentPosition());

                } else {
                    System.out.println("Czy wchodzę tutaj?");
                    currentDestination = crossRoadPoint;
                    beenIncrossRoad = false;
                    delta_x = getCurrentDestination().getX() - getCurrentPosition().getX();
                    delta_y = getCurrentDestination().getY() - getCurrentPosition().getY();
                    goal_dist = Math.sqrt((delta_x * delta_x) + (delta_y * delta_y));

//                    setCurrentPosition(getCurrentDestination());
                }
                Thread.sleep(35);
            } catch (InterruptedException e) {
                e.printStackTrace();
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
}
