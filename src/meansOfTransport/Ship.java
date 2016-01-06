package meansOfTransport;

import javafx.scene.layout.Pane;
import spawners.Airport;
import spawners.Harbor;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by kadash on 18.10.15.
 */
public abstract class Ship extends MeansOfTransport {
    protected int maxVelocity;
    protected ArrayList<Harbor> route = new ArrayList<Harbor>();
    public Ship(ArrayList<Harbor> allDestination, Pane context) {
        super(context);
    }
    public void goToNextHarbor(){

    }

}
