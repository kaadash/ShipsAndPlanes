package meansOfTransport;

import javafx.scene.image.*;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

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

    public MeansOfTransport(Pane context){
        this.context = context;
    }

    public void remove () {

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
}
