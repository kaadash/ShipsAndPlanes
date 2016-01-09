package meansOfTransport;

import helpers.Drawable;
import helpers.MutableDouble;
import javafx.scene.layout.Pane;
import java.awt.Point;
import java.awt.geom.Point2D;

/**
 * Created by kadash on 18.10.15.
 */
public abstract class MeansOfTransport extends Drawable implements Runnable {

    protected Point2D currentDestination;

    protected Point crossRoadPoint;

    protected int ID;

    protected boolean asyncWasReportSent;

    public MeansOfTransport(Pane context){
        super(context);
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



    public Point2D getCurrentDestination() {
        return currentDestination;
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
