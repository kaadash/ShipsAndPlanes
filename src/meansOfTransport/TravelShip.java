package meansOfTransport;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by kadash on 18.10.15.
 */
public class TravelShip extends Ship {
    public TravelShip(ArrayList<Point> allDestination) {
        super(allDestination);
    }
    private int maxPassengers;
    private int currentNumberOfPassengers;
    private String companyName;

}
