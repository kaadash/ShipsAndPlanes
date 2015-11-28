package meansOfTransport;

import javafx.scene.image.*;

import java.awt.Point;
import java.util.ArrayList;

/**
 * Created by kadash on 18.10.15.
 */
public class PassengerPlane extends Aeroplane {
    private int maxPassengers;
    private int currentPassengers;

    private final String imagePath = "images/aircraft.png";

    public PassengerPlane(ArrayList<Point> allDestination) {
        super(allDestination);
        System.out.print(this.currentPosition);
    }
    public Point findNearestAirport() {
        Point nearestAirport = new Point();
        nearestAirport.x = 12;
        nearestAirport.y = 12;
        return nearestAirport;
    }
    public String getImagePath() {
        return imagePath;
    }
}
