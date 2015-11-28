package meansOfTransport;

import javafx.scene.image.*;
import javafx.scene.image.Image;

import java.awt.Point;
import java.util.ArrayList;

/**
 * Created by kadash on 18.10.15.
 */
public abstract class MeansOfTransport {
    protected Point currentPosition;

    protected Point currentDestination;

    protected ArrayList<Point> route = new ArrayList<Point>();

    protected static int ID;
    public MeansOfTransport(ArrayList<Point> allDestination){

    }

    public Point[] generateRoute() {
        Point[] route = new Point[2];
        return route;
    }

    public void movement(){

    }

    public void remove () {

    }

    public void draw() {

    }
    public Point getCurrentPosition() {
        return currentPosition;
    }

    public Point getCurrentDestination() {
        return currentDestination;
    }
}
