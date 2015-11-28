package spawners;

import javafx.scene.image.*;

import java.awt.Point;

/**
 * Created by kadash on 18.10.15.
 */
public class CivilAirport extends Airport{
    private final String imagePath = "images/AIRPORT.jpg";

    public CivilAirport(Point civilAirportPosition) {
        super(civilAirportPosition);
    }

    public String getImagePath() {
        return imagePath;
    }
}
