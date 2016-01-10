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

    private boolean isOnDestinationPoint;

    private int routePointer;

    private enum TypeOfTravel {
        PRIVATE, BUSINESS
    }
    private TypeOfTravel eTypeOfTravel;

    /**
     * randomize all fields of object Passengers
     * - position
     * - age
     * - name
     * - type of travel
     * @param currentPosition
     */
    public Passenger(Point currentPosition) {
        this.PESEL = generatePESEL();
        this.name = generateName();
        this.surname = generateName();
        this.age = generateAge();
        isOnDestinationPoint = false;
        generateRoute(currentPosition);
        if((int)(Math.random() * 100) % 2 == 0) {
            this.eTypeOfTravel = TypeOfTravel.BUSINESS;
        }
        else {
            this.eTypeOfTravel = TypeOfTravel.PRIVATE;
        }
    }

    /**
     * generate PESEL which has 9 random numbers
     * @return
     */
    public String generatePESEL(){
        String PESELNumber = "";
        while(PESELNumber.length() < 9) {
            PESELNumber += (int)(Math.random() * 10);
        }
        return PESELNumber;
    }

    /**
     * Generate name using some const strings
     * @return
     */
    public String generateName() {
        String names[] = {"Anna", "Artur", "Jan", "Iza", "Maciej", "Monika", "Kuba", "Mateusz"};
        return names[(int)(Math.random() * names.length)];
    }

    /**
     * generate travel route which route size is randomized
     * and the smallest route contains 4 destination points
     * @param startingPosition
     */
    public void generateRoute(Point startingPosition) {
        this.route = Map.getDestinationCord(Map.getCivilAirports());
        this.routePointer = 0;
        int lengthOfTravel = Math.abs((int) (Math.random() * route.size() - 1) - 4);
        for (int i = lengthOfTravel; i >= 0; i--) {
            this.route.remove(this.route.size() - 2);
        }
        Collections.shuffle(this.route);
        this.route.add(0, startingPosition);
        this.route.add(startingPosition);
        changeRoute();
    }

    /**
     * generate age between 0 - 100 years old
     * @return
     */
    public int generateAge() {
        return (int)(Math.random() * 100);
    }

    /**
     * Change route when passenger is on his current destination
     */
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

    /**
     * Main logic where is set how long should be in the city each passenger
     * depending on type of travel
     */
    @Override
    public void run() {
        while(true) {
            if(isOnDestinationPoint){
//                Depends on type of travel different time spend in destination city
                switch (eTypeOfTravel) {
                    case PRIVATE:
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        break;
                    case BUSINESS:
                        try {
                            Thread.sleep(10000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        break;
                    default:
                        System.out.println("error with enum type");
                        break;
                }
                isOnDestinationPoint = false;
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
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

    public void setCurrentPosition(Point2D currentPosition) {
        this.currentPosition = currentPosition;
    }

    public void setCurrentDestination(Point2D currentDestination) {
        this.currentDestination = currentDestination;
    }

    public boolean isOnDestinationPoint() {
        return isOnDestinationPoint;
    }

    public void setOnDestinationPoint(boolean isOnDestinationPoint) {
        this.isOnDestinationPoint = isOnDestinationPoint;
    }
}
