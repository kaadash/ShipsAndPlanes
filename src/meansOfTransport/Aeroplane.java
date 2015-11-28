package meansOfTransport;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.image.*;
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

    public void animate(Point destination, ImageView imageView) {
        Timeline timeline = new Timeline();
        System.out.print(destination);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(true);
        KeyValue kvX = new KeyValue(imageView.translateXProperty(), destination.getX());
        KeyValue kvY = new KeyValue(imageView.translateYProperty(), destination.getY());

        final KeyFrame kf = new KeyFrame(Duration.millis(3000), kvX, kvY);
        timeline.getKeyFrames().add(kf);
        timeline.play();
    }

    public void reportIssue () {

    }

    public void landOnNearestAirport () {

    }

    public void restoreFuel () {

    }

}
