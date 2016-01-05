package meansOfTransport;

import javafx.scene.layout.Pane;
import spawners.Airport;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by kadash on 18.10.15.
 */
public abstract class Ship extends MeansOfTransport {
    protected int maxVelocity;
    public Ship(ArrayList<Airport> allDestination, Pane context) {
        super(context);
    }
    public void goToNextHarbor(){

    }

}
