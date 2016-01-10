package meansOfTransport;

import javafx.scene.layout.Pane;
import spawners.Airport;
import spawners.Harbor;

import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

/**
 * Created by kadash on 18.10.15.
 */
public abstract class Ship extends MeansOfTransport {
    protected int maxVelocity;
    protected boolean beenIncrossRoad;
    public static Semaphore aeroplaneCrossRoads = new Semaphore(1);
    protected ArrayList<Harbor> route = new ArrayList<Harbor>();
    /**
     * set max velocity of ship by randomize them
     * @param context
     */
    public Ship(Pane context) {
        super(context);
        this.maxVelocity = (int)(Math.abs((Math.random() * 50) - 100));
    }

    public int getMaxVelocity() {
        return maxVelocity;
    }

    public void setMaxVelocity(int maxVelocity) {
        this.maxVelocity = maxVelocity;
    }

}
