package spawners;

import helpers.Drawable;
import javafx.scene.layout.Pane;

import java.awt.*;

/**
 * Created by kadash on 06.01.16.
 */
public abstract class Spawner extends Drawable {

    protected Point position;

    protected Point leftLaneStartingPoint;

    protected Point rightLaneStartingPoint;

    protected Point leftLaneEndingPoint;

    protected Point rightLaneEndingPoint;

    public Spawner(Point airportPosition, Pane context) {
        super(context);
        this.currentPosition = airportPosition;
        this.position = airportPosition;
    }

    public void setLeftLaneStartingPoint(Point leftLaneStartingPoint) {
        this.leftLaneStartingPoint = leftLaneStartingPoint;
    }

    public Point getRightLaneStartingPoint() {
        return rightLaneStartingPoint;
    }

    public void setRightLaneStartingPoint(Point rightLaneStartingPoint) {
        this.rightLaneStartingPoint = rightLaneStartingPoint;
    }

    public Point getLeftLaneEndingPoint() {
        return leftLaneEndingPoint;
    }

    public void setLeftLaneEndingPoint(Point leftLaneEndingPoint) {
        this.leftLaneEndingPoint = leftLaneEndingPoint;
    }

    public Point getRightLaneEndingPoint() {
        return rightLaneEndingPoint;
    }

    public void setRightLaneEndingPoint(Point rightLaneEndingPoint) {
        this.rightLaneEndingPoint = rightLaneEndingPoint;
    }

    public Point getLeftLaneStartingPoint() {
        return leftLaneStartingPoint;
    }
    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }
}
