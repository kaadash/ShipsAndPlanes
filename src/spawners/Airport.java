package spawners;

import javafx.scene.image.*;
import javafx.scene.layout.Pane;

import java.awt.Point;


/**
 * Created by kadash on 18.10.15.
 */
public abstract class Airport {

    protected Point position;

    protected String imagePath;

    protected int maxCapacity;

    protected int currentNumberOfPlanes;
    public Airport(Point airportPosition) {
        this.position = airportPosition;
    }
    public void draw (Pane context, String imagePath) {
        Image image = new Image(imagePath);
        ImageView imageView = new ImageView(image);

        imageView.setFitHeight(50);
        imageView.setFitWidth(50);
        imageView.setLayoutX(this.getPosition().getX());
        imageView.setLayoutY(this.getPosition().getY());

        context.getChildren().add(imageView);
    }

    public Point getPosition() {
        return position;
    }
    public void setPosition(Point position) {
        this.position = position;
    }

    public String getImagePath() {
        return imagePath;
    }

}
