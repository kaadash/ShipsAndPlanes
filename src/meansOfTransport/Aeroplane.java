package meansOfTransport;
import javafx.scene.image.*;
import javafx.scene.layout.Pane;
import spawners.Airport;
import travelDependency.Passenger;
import java.awt.Point;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public abstract class Aeroplane extends MeansOfTransport{
    protected int numberOfStaff;

    protected boolean beenIncrossRoad;

    protected int fuel;

    protected ArrayList<Airport> route = new ArrayList<Airport>();

    public static Semaphore aeroplaneCrossRoads = new Semaphore(1);

    public Aeroplane( Pane context) {
        super(context);

        this.crossRoadPoint = new Point(550, 350);
        this.fuel = 1000;
        this.beenIncrossRoad = false;
        this.asyncWasReportSent = false;

    }

    public void lossOfFuel() {
        this.fuel -= 4;
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

    public ArrayList<Airport> getRoute() {
        return route;
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
}