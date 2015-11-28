package spawners;

import javafx.scene.image.*;

import java.awt.Point;


/**
 * Created by kadash on 18.10.15.
 */
public abstract class Airport {

    protected Point position;
    protected int maxCapacity;
    protected int currentNumberOfPlanes;
    public Airport(Point airportPosition) {
        this.position = airportPosition;
    }
    public ImageView draw (String imagePath) {
        Image image = new Image(imagePath);
        ImageView imageView = new ImageView(image);
        return imageView;
    }
    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

}
