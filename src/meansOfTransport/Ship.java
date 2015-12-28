package meansOfTransport;

import javafx.scene.layout.Pane;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by kadash on 18.10.15.
 */
public abstract class Ship extends MeansOfTransport {
    protected int maxVelocity;
    public Ship(ArrayList<Point> allDestination, Pane context) {
        super(allDestination, context);
    }
    public void goToNextHarbor(){

    }

}
