package meansOfTransport;

import controllers.AeroplaneController;
import helpers.MutableDouble;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.image.*;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.io.IOException;

/**
 * Created by kadash on 18.10.15.
 */
public abstract class MeansOfTransport implements Runnable {
    protected Point2D currentPosition;

    protected Point2D currentDestination;

    protected Point crossRoadPoint;

    protected Point tempPosition;

    protected Pane context;

    protected int ID;

    protected boolean asyncWasReportSent;

    protected ImageView imageViewMeanOfTransport = new ImageView(new Image("images/aircraft.png"));

    public MeansOfTransport(Pane context){
        this.context = context;
        int sizeImage = 32;
        imageViewMeanOfTransport.setFitHeight(sizeImage);
        imageViewMeanOfTransport.setFitWidth(sizeImage);
        context.getChildren().add(imageViewMeanOfTransport);
    }

    public void animate() {
        imageViewMeanOfTransport.setLayoutX(this.getCurrentPosition().getX());
        imageViewMeanOfTransport.setLayoutY(this.getCurrentPosition().getY());
    }
    protected void addClickActionToObject(TabPane root, String windowTitle){
        imageViewMeanOfTransport.setOnMouseClicked(event -> {
            Stage stage = new Stage();
            stage.setTitle(windowTitle);
            stage.setScene(new Scene(root, 450, 450));
            stage.show();
        });
    }

    protected void updatePositionOnMap(MutableDouble deltaX, MutableDouble deltaY, MutableDouble goalDist) {
        deltaX.setValue(getCurrentDestination().getX() - getCurrentPosition().getX());
        deltaY.setValue(getCurrentDestination().getY() - getCurrentPosition().getY());
        goalDist.setValue(Math.sqrt((deltaX.getValue() * deltaX.getValue()) + (deltaY.getValue() * deltaY.getValue())));
    }

    protected void updateCurrentCordinates(MutableDouble currentDeltaX, MutableDouble currentDeltaY, MutableDouble dist) {
        currentDeltaX.setValue(getCurrentDestination().getX() - getCurrentPosition().getX());
        currentDeltaY.setValue(getCurrentDestination().getY() - getCurrentPosition().getY());
        dist.setValue(Math.sqrt((currentDeltaX.getValue() * currentDeltaX.getValue()) + (currentDeltaY.getValue() * currentDeltaY.getValue())));
    }

    protected void updateStepToMove(MutableDouble ratio, MutableDouble deltaX, MutableDouble deltaY,
                                    MutableDouble xMove, MutableDouble yMove, MutableDouble goalDist, int speedPerTick) {
        ratio.setValue(speedPerTick / goalDist.getValue());
        xMove.setValue(ratio.getValue() * deltaX.getValue());
        yMove.setValue(ratio.getValue() * deltaY.getValue());
    }

    public void setCurrentDestination(Point2D currentDestination) {
        this.currentDestination = currentDestination;
    }

    public Point2D getCurrentPosition() {
        return currentPosition;
    }

    public Point2D getCurrentDestination() {
        return currentDestination;
    }

    public void setCurrentPosition(Point2D currentPosition) {
        this.currentPosition = currentPosition;
    }

    public Point getTempPosition() {
        return tempPosition;
    }

    public Point getCrossRoadPoint() {
        return crossRoadPoint;
    }

    public void setCrossRoadPoint(Point crossRoadPoint) {
        this.crossRoadPoint = crossRoadPoint;
    }

    public int getID() {
        return ID;
    }

    public boolean isAsyncWasReportSent() {
        return asyncWasReportSent;
    }

    public void setAsyncWasReportSent(boolean asyncWasReportSent) {
        this.asyncWasReportSent = asyncWasReportSent;
    }
}
