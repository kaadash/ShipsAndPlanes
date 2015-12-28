package controllers;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import meansOfTransport.MilitaryAircraft;
import spawners.CivilAirport;
import spawners.Harbor;

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

    private ArrayList<MilitaryAircraft> militaryAircrafts;
    private ArrayList<Harbor> harbors;
    public static void prepareMap(Pane stack) {
        generateCivilAirports(10);
        for (CivilAirport airport:civilAirports) {
            ImageView imageToAdd = airport.draw(airport.getImagePath());
            imageToAdd.setFitHeight(50);
            imageToAdd.setFitWidth(50);
            imageToAdd.setLayoutX(airport.getPosition().getX());
            imageToAdd.setLayoutY(airport.getPosition().getY());
            stack.getChildren().add(imageToAdd);
        }
    }

    public static void generateCivilAirports(int numberOfAirports) {
        Point[] cords = new Point[10];
        int distance = 250;
        double sizeOfStep = Math.PI/numberOfAirports * 2;
        double angle = 0;

//        Generator of city in circle
        for (int i = 0; i < numberOfAirports; i++) {
            angle +=sizeOfStep;
            int y = (int)Math.floor( distance * Math.sin( angle ) );
            int x = (int)Math.floor( distance * Math.cos( angle ) );
            cords[i] = new Point(x, y);
        }

//        Adding cities into global civilAirports
        for (Point cord: cords){
            cord.setLocation(cord.getX() + 550, cord.getY() + 350);
            CivilAirport airport = new CivilAirport(cord);
            civilAirports.add(airport);
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
}
