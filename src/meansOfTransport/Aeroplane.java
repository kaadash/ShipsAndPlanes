package meansOfTransport;
import javafx.scene.image.*;
import javafx.scene.layout.Pane;
import travelDependency.Passenger;
import java.awt.Point;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public abstract class Aeroplane extends MeansOfTransport{
    protected int numberOfStaff;

    protected boolean beenIncrossRoad;

    protected int fuel;
    protected boolean asyncWasReportSent;

    private ArrayList<Passenger> passengersOnBoard = new ArrayList<Passenger>();
    protected ImageView imageViewPlane = new ImageView(new Image("images/aircraft.png"));

    public static Semaphore aeroplaneCrossRoads = new Semaphore(1);

    public Aeroplane(ArrayList<Point> allDestination, Pane context) {
        super(allDestination, context);
        for (int i = 0; i < allDestination.size(); i++) {
            this.route.add(allDestination.get(i));
        }
        this.crossRoadPoint = new Point(550, 350);
        this.currentDestination = this.crossRoadPoint;
        this.currentPosition = this.route.get(0);
        this.fuel = 1000;
        this.beenIncrossRoad = false;
        this.asyncWasReportSent = false;
        int sizeImage = 50;
        imageViewPlane.setFitHeight(sizeImage);
        imageViewPlane.setFitWidth(sizeImage);
        context.getChildren().add(imageViewPlane);
    }

    public void lossOfFuel() {
        this.fuel -= 4;
    }

    public Point findNearestAirport() {
        Point nearestAirport = new Point();
        nearestAirport.x = 12;
        nearestAirport.y = 12;
        return nearestAirport;
    }

    public void reportIssue() {

    }

    public void landOnNearestAirport() {

    }


    public void restoreFuel() {
        this.fuel = 1000;
    }

    @Override
    public void run() {

    }

    public int getFuel() {
        return fuel;
    }





    public void setFuel(int fuel) {
        this.fuel = fuel;
    }

    public boolean isBeenIncrossRoad() {
        return beenIncrossRoad;
    }

    public void setBeenIncrossRoad(boolean beenIncrossRoad) {
        this.beenIncrossRoad = beenIncrossRoad;
    }

    public boolean isAsyncWasReportSent() {
        return asyncWasReportSent;
    }

    public void setAsyncWasReportSent(boolean asyncWasReportSent) {
        this.asyncWasReportSent = asyncWasReportSent;
    }
}