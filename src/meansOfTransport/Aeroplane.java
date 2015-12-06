package meansOfTransport;

import javafx.animation.*;
import javafx.scene.image.*;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by kadash on 18.10.15.
 */
public class Aeroplane extends MeansOfTransport {
    protected int numberOfStaff;
    protected int fuel;
    protected Point nextStation;
    public Aeroplane(ArrayList<Point> allDestination)
    {
        super(allDestination);
        for (int i = 0; i < allDestination.size(); i++) {
            this.route.add(allDestination.get(i));
        }
        Collections.shuffle(this.route);
        this.currentDestination = this.route.get(1);
        this.currentPosition = this.route.get(0);
    }
    public void lossOfFuel() {
        this.fuel--;
    }

    public Point findNearestAirport () {
        Point nearestAirport = new Point();
        nearestAirport.x = 12;
        nearestAirport.y = 12;
        return nearestAirport;
    }

    public ImageView draw (String imagePath) {
        Image image = new Image(imagePath);
        ImageView imageView = new ImageView(image);
        return imageView;
    }

    public void animate(ImageView planeImage) {
        planeImage.setLayoutX(this.getCurrentPosition().getX());
        planeImage.setLayoutY(this.getCurrentPosition().getY());

        Path path = new Path();
        path.getElements().add(new MoveTo(0, 0));
        path.getElements().add(new LineTo(this.getCurrentDestination().getX() - this.getCurrentPosition().getX(),
                this.getCurrentDestination().getY() - this.getCurrentPosition().getY() ));
        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.millis(4000));
        pathTransition.setPath(path);
        pathTransition.setNode(planeImage);
        pathTransition.play();
    }

    public void reportIssue () {

    }

    public void landOnNearestAirport () {

    }

    public void restoreFuel () {

    }

}
