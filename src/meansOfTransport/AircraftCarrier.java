package meansOfTransport;

import controllers.AircraftCarrierController;
import helpers.MutableDouble;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import spawners.Harbor;

import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by kadash on 18.10.15.
 */
public class AircraftCarrier extends Ship {
    private String ammoType;

    private final String imagePath = "images/militarymightjl2.jpg";
    public AircraftCarrier(ArrayList<Harbor> allDestination, Pane context, int id) {
        super(context);
        for (Harbor harbor : allDestination) {
            this.route.add(harbor);
        }
        Collections.shuffle(this.route);
        this.imageViewOfObject.setImage(new Image(imagePath));
        this.ID = id;
        this.ammoType = generateAmmoType();
        this.currentPosition = this.route.get(0).getRightLaneStartingPoint();
        this.currentDestination = this.route.get((int)(Math.random() * this.route.size() - 1)).getLeftLaneEndingPoint();
    }
    private String generateAmmoType() {

        String firstPart[] = {"Super", "Fine", "Hello", "FaceBook", "AreYouSure"};
        String secondPart[] = {"Grenade", "Sniper", "Boom", "MegaBoom", "Nuke"};
        return firstPart[(int)(Math.random() * firstPart.length - 1)] + secondPart[(int)(Math.random() * firstPart.length - 1)];
    }

    public void openInformationPanel() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            TabPane root = fxmlLoader.load(getClass().getResource("aircraftCarrierLayout.fxml").openStream());
            AircraftCarrierController aircraftCarrierController = (AircraftCarrierController) fxmlLoader.getController();
            aircraftCarrierController.updateView(this);
            addClickActionToObject(root, "Aircraft Carrier Dashboard");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        int destinationPointer = 1;
        int travelCounter = 0;

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
                            currentDestination = route.get(destinationPointer).getRightLaneStartingPoint();
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

    public String getAmmoType() {
        return ammoType;
    }

    public void setAmmoType(String ammoType) {
        this.ammoType = ammoType;
    }
}
