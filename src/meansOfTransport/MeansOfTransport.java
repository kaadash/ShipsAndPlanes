package meansOfTransport;

import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.image.*;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.awt.Point;
import java.awt.geom.Point2D;

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
