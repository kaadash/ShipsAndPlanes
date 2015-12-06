package meansOfTransport;

import javafx.animation.*;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.*;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.xml.soap.Node;
import java.awt.Point;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by kadash on 18.10.15.
 */
public class Aeroplane extends MeansOfTransport {
    protected IntegerProperty numberOfStaff = new SimpleIntegerProperty();
    protected DoubleProperty fuel = new SimpleDoubleProperty();


    // START TEST
    private PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    public void setTempPosition(Point position) {
        pcs.firePropertyChange("tempPosition", this.tempPosition, position);
        this.tempPosition = position;
    }
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }
// END TEST




    public Aeroplane(ArrayList<Point> allDestination)
    {
        super(allDestination);
        for (int i = 0; i < allDestination.size(); i++) {
            this.route.add(allDestination.get(i));
        }
        Collections.shuffle(this.route);
        this.currentDestination = this.route.get(1);
        this.currentPosition = this.route.get(0);
        this.fuel.setValue(1000);
    }
    public void lossOfFuel() {
        int currentValue = this.fuel.intValue();
        this.fuel.setValue(currentValue - 1);
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

    public void openInformationPanel(ImageView planeImage){
        planeImage.setOnMouseClicked(event->{
            Label fuelLabel = new Label(this.fuel.getValue().toString());
            fuelLabel.setTranslateX(50);
            Label currentDestinationLabel = new Label();
            Label currentPositionLabel = new Label();
            currentDestinationLabel.setTranslateX(150);
            currentDestinationLabel.setTranslateY(100);
            currentPositionLabel.setTranslateX(150);
            currentPositionLabel.setTranslateY(50);

            GridPane root;
            try {
                root = FXMLLoader.load(getClass().getResource("aeroplaneLayout.fxml"));
                root.getChildren().add(fuelLabel);
                root.getChildren().add(currentDestinationLabel);
                root.getChildren().add(currentPositionLabel);
                this.fuel.addListener(new ChangeListener() {
                    @Override
                    public void changed(ObservableValue o, Object oldVal,
                                        Object newVal) {
                        fuelLabel.setText(o.getValue().toString());
                    }
                });
                this.addPropertyChangeListener((evt) -> {
                    currentDestinationLabel.setText(this.getCurrentDestination().toString());
                    currentPositionLabel.setText(this.getTempPosition().toString());
                });
                Stage stage = new Stage();
                stage.setTitle("Aeroplane Panel");
                stage.setScene(new Scene(root, 450, 450));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void reportIssue () {

    }

    public void landOnNearestAirport () {

    }

    public void restoreFuel () {

    }

    public void setFuel(double fuel) {
        this.fuel.set(fuel);
    }

}
