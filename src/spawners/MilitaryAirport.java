package spawners;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import java.awt.*;


/**
 * Created by kadash on 18.10.15.
 */
public class MilitaryAirport extends Airport {
    public MilitaryAirport(Point militaryAirportPosition, Pane context) {
        super(militaryAirportPosition, context);
        this.imagePath = "images/AIRPORT.jpg";
        this.imageViewOfObject.setImage(new Image(this.imagePath));
    }
}
