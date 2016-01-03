package controllers;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import meansOfTransport.MilitaryAircraft;
import spawners.CivilAirport;
import spawners.Harbor;
import spawners.MilitaryAirport;

import java.awt.Point;
import java.util.ArrayList;

/**
 * Created by kadash on 21.10.15.
 */
public class Map {
    /**
     * draw Harbours and Airports
     */
    private String backgroundImgPath;
    private int sizeX;
    private int sizeY;

    private static ArrayList<CivilAirport> civilAirports = new ArrayList<CivilAirport>();

    private static ArrayList<MilitaryAirport> militaryAirports = new ArrayList<MilitaryAirport>();

    private ArrayList<MilitaryAircraft> militaryAircrafts;

    private ArrayList<Harbor> harbors;

    public static void prepareMap(Pane stack) {
        generateCivilAirports(10, stack);
        generateMilitaryAirports(10, stack);
    }

    public static ArrayList<Point> generateCordsInCircle(int numberOfAirports, int distance) {
        ArrayList<Point> cords = new ArrayList<Point>();
        double sizeOfStep = Math.PI/numberOfAirports * 2;
        double angle = 0;

//        Generator of city in circle
        for (int i = 0; i < numberOfAirports; i++) {
            angle +=sizeOfStep;
            int y = (int)Math.floor( distance * Math.sin( angle ) );
            int x = (int)Math.floor( distance * Math.cos( angle ) );
            cords.add(new Point(x, y));
        }
        return cords;
    }

    public static void generateCivilAirports(int numberOfAirports, Pane context) {

        ArrayList<Point> cords = generateCordsInCircle(numberOfAirports, 250);
//        Adding cities into global civilAirports
        for (Point cord: cords){
            cord.setLocation(cord.getX() + 550, cord.getY() + 350);
            CivilAirport airport = new CivilAirport(cord);
            airport.draw(context, airport.getImagePath());
            civilAirports.add(airport);
        }
    }

    public static void generateMilitaryAirports(int numberOfAirports, Pane context) {
        ArrayList<Point> cords = generateCordsInCircle(numberOfAirports, 350);
//        Adding cities into global military airports
        for (Point cord: cords){
            cord.setLocation(cord.getX() + 550, cord.getY() + 350);
            MilitaryAirport airport = new MilitaryAirport(cord);
            airport.draw(context, airport.getImagePath());
            militaryAirports.add(airport);
        }
    }

    public static ArrayList<Point> getDestinationCord (ArrayList<CivilAirport> destination) {
        ArrayList<Point> cordinates = new ArrayList<Point>();
        for (int i = 0; i < destination.size(); i++) {
            cordinates.add(destination.get(i).getPosition());
        }
        return cordinates;
    }

    public static ArrayList<CivilAirport> getCivilAirports() {
        return civilAirports;
    }
    public static ArrayList<MilitaryAirport> getMilitaryAirports() {
        return militaryAirports;
    }
}
