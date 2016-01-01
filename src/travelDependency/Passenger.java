package travelDependency;

import controllers.Dashboard;
import controllers.Map;
import javafx.application.Platform;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Created by kadash on 21.10.15.
 */
public class Passenger implements Runnable {
    private String name;
    private String surname;

    private String PESEL;

    private ArrayList<Point> route = new ArrayList<Point>();
    private Point2D currentPosition;

    private Point2D currentDestination;

    private int routePointer;

    public Passenger() {
        this.PESEL = generatePESEL();
        this.name = generateName();
        this.surname = generateName();
        this.route = Map.getDestinationCord(Map.getCivilAirports());
//        Mocku up
        Collections.shuffle(this.route);
        this.changeRoute();
        System.out.println(this.currentPosition);
        System.out.println(this.currentDestination);
    }

    public String generatePESEL(){
        String PESELNumber = "";
        while(PESELNumber.length() < 9) {
            PESELNumber += (int)(Math.random() * 10);
        }
        return PESELNumber;
    }

    public String generateName() {
        String names[] = {"Anna", "Artur", "Jan", "Iza"};
        return names[(int)(Math.random() * names.length)];
    }

    public void changeRoute() {
        if(routePointer == this.route.size()) {
            routePointer = 0;
        }
        this.currentDestination = this.route.get(routePointer + 1);
        this.currentPosition = this.route.get(routePointer);
        this.routePointer++;
    }

    @Override
    public void run() {

    }

    public Point2D getCurrentDestination() {
        return currentDestination;
    }
    public Point2D getCurrentPosition() {
        return currentPosition;
    }
    public String getPESEL() {
        return PESEL;
    }
    public int getRoutePointer() {
        return routePointer;
    }

    public void setRoutePointer(int routePointer) {
        this.routePointer = routePointer;
    }
}
