package travelDependency;

import java.awt.*;

/**
 * Created by kadash on 18.10.15.
 */
public class TravelType {
    private String type;
    private Point[] path;
    private int timeSpentInStation;
    public int getPrivateTravelTime () {
        return 1;
    }
    public int getBussinessTravelTime () {
        return 1;
    }

    public Point[] getNewRandomPath() {
        Point[] path = new Point[2];
        return path;
    }
}
