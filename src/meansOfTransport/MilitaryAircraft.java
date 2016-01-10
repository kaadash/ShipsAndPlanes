package meansOfTransport;

import controllers.Dashboard;
import controllers.MilitaryAircraftController;
import helpers.MutableDouble;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import spawners.MilitaryAirport;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by kadash on 18.10.15.
 */
public class MilitaryAircraft extends Aeroplane  {
    private String ammoType;

    private final String imagePath = "images/enemy.png";

    public MilitaryAircraft(ArrayList<MilitaryAirport> allDestination, Pane context, int id,
                            int IDOfSpawningAircraftCarrier) {
        super(context);
        for (MilitaryAirport militaryAirport : allDestination) {
            this.route.add(militaryAirport );
        }
        this.ID = id;
        this.imageViewOfObject.setImage(new Image(imagePath));
        AircraftCarrier spawner = Dashboard.getAircraftCarriers().get(IDOfSpawningAircraftCarrier);
        this.currentPosition = spawner.getCurrentPosition();
        this.ammoType = spawner.getAmmoType();
        this.currentDestination = this.route.get(0).getRightLaneEndingPoint();
    }

    @Override
    public void run() {
        int destinationPointer = 1;
        int travelCounter = 0;
//        Check if in starting city, there are passengers wanting to travel

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

                } else {
                    travelCounter++;
                    switch (travelCounter) {
                        case 3:
                            int baseValue = route.get(destinationPointer).getCurrentVehicles();
                            route.get(destinationPointer).setCurrentVehicles(baseValue + 1);
                            Thread.sleep(3000);
                            currentDestination = route.get(destinationPointer).getRightLaneStartingPoint();
                            restoreFuel();
                            break;
                        case 4:
                            baseValue = route.get(destinationPointer).getCurrentVehicles();
                            route.get(destinationPointer).setCurrentVehicles(baseValue - 1);
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
                    openInformationPanel();
                    animate();
                    lossOfFuel();
                }
            });
        }
    }
    public void openInformationPanel() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            TabPane root = fxmlLoader.load(getClass().getResource("militaryAircraftLayout.fxml").openStream());
            MilitaryAircraftController militaryAircraftController = (MilitaryAircraftController) fxmlLoader.getController();
            militaryAircraftController.updateView(this);
            addClickActionToObject(root, "Military Aircraft Dashboard");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String getAmmoType() {
        return ammoType;
    }

    public void setAmmoType(String ammoType) {
        this.ammoType = ammoType;
    }
}
