package meansOfTransport;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by kadash on 18.10.15.
 */
public class AircraftCarrier extends Ship{
    private String ammoType;
    public AircraftCarrier(ArrayList<Point> allDestination) {
        super(allDestination);
    }
    public Point getRandomHarbor() {
        return new Point();
    }

//    public MilitaryAircraft spawnMilitaryAircraft () {
//        return new MilitaryAircraft();
//    }

}
