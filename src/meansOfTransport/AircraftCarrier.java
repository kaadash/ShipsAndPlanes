package meansOfTransport;

import javafx.scene.layout.Pane;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by kadash on 18.10.15.
 */
public class AircraftCarrier extends Ship{
    private String ammoType;
    public AircraftCarrier(ArrayList<Point> allDestination, Pane context) {
        super(allDestination, context);
    }
    public Point getRandomHarbor() {
        return new Point();
    }

//    public MilitaryAircraft spawnMilitaryAircraft () {
//        return new MilitaryAircraft();
//    }
    @Override
    public void run() {
        while(true){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
