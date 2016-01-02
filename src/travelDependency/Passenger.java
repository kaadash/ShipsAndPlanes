package travelDependency;

import controllers.Dashboard;
import controllers.Map;
import javafx.application.Platform;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by kadash on 21.10.15.
 */
public class Passenger implements Runnable {
    private String name;

    private String surname;

    private String PESEL;

    private int age;

    private ArrayList<Point> route = new ArrayList<Point>();

    private Point2D currentPosition;

    private Point2D currentDestination;

    private int routePointer;

    public Passenger() {
        this.PESEL = generatePESEL();
        this.name = generateName();
        this.surname = generateName();
        this.age = generateAge();
        this.route = Map.getDestinationCord(Map.getCivilAirports());
        Collections.shuffle(this.route);
        this.changeRoute();
    }
    public String generatePESEL(){
        String PESELNumber = "";
        while(PESELNumber.length() < 9) {
            PESELNumber += (int)(Math.random() * 10);
        }
        return PESELNumber;
    }

    public String generateName() {
        String names[] = {"Anna", "Artur", "Jan", "Iza", "Maciej", "Monika", "Kuba"};
        return names[(int)(Math.random() * names.length)];
    }

    public int generateAge() {
        return (int)(Math.random() * 100);
    }

    public void changeRoute() {
        if(routePointer == this.route.size()) {
            routePointer = 0;
            this.route = Map.getDestinationCord(Map.getCivilAirports());
            Collections.shuffle(this.route);
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

    public void setPESEL(String PESEL) {
        this.PESEL = PESEL;
    }

    public int getRoutePointer() {
        return routePointer;
    }
    public void setRoutePointer(int routePointer) {
        this.routePointer = routePointer;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public ArrayList<Point> getRoute() {
        return route;
    }

    public void setRoute(ArrayList<Point> route) {
        this.route = route;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
