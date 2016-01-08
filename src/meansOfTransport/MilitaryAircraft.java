package meansOfTransport;

import controllers.Dashboard;
import javafx.application.Platform;
import javafx.scene.image.*;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import spawners.Airport;
import spawners.Harbor;
import spawners.MilitaryAirport;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * Created by kadash on 18.10.15.
 */
public class MilitaryAircraft extends Aeroplane  {
    private String ammoType;

    private final String imagePath = "images/airplane.png";

    public MilitaryAircraft(ArrayList<MilitaryAirport> allDestination, Pane context, int id) {
        super(context);
        for (MilitaryAirport militaryAirport : allDestination) {
            this.route.add(militaryAirport );
        }
        this.ID = id;
        this.imageViewMeanOfTransport.setImage(new Image(imagePath));
        int randomAircraftCarrierIndex = (int)(Math.random() * Dashboard.getAircraftCarriers().size() - 1);
        this.currentPosition = Dashboard.getAircraftCarriers().get(randomAircraftCarrierIndex).currentPosition;
        this.currentDestination = this.route.get(0).getRightLaneEndingPoint();
    }
    public void animate() {
        imageViewMeanOfTransport.setLayoutX(this.getCurrentPosition().getX());
        imageViewMeanOfTransport.setLayoutY(this.getCurrentPosition().getY());
//        openInformationPanel();
    }
    @Override
    public void run() {
        int destinationPointer = 1;
        int travelCounter = 0;
//        Check if in starting city, there are passengers wanting to travel

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

                    if(dist < 110 && !beenIncrossRoad && dist > 2 ) {
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
                            currentDestination = route.get(destinationPointer).getRightLaneStartingPoint();
                            restoreFuel();
                            break;
                        case 4:
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
                Thread.sleep(35);
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
                }
            });
        }
    }
    public String getAmmoType() {
        return ammoType;
    }

    public void setAmmoType(String ammoType) {
        this.ammoType = ammoType;
    }

}
