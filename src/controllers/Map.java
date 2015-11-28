package controllers;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import meansOfTransport.MilitaryAircraft;
import spawners.Airport;
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
    public static void prepareMap(StackPane stack) {
        generateCivilAirports(5);
        for (CivilAirport airport:civilAirports) {
            ImageView imageToAdd = airport.draw(airport.getImagePath());
            imageToAdd.setFitHeight(80);
            imageToAdd.setFitWidth(80);
            imageToAdd.setTranslateX(airport.getPosition().getX());
            imageToAdd.setTranslateY(airport.getPosition().getY());
            stack.getChildren().add(imageToAdd);
        }
    }

    public static void generateCivilAirports(int numberOfAirports) {
//        for (int i = 0; i < numberOfAirports; i++) {
//            int x = (int)(Math.random() * 500) - 250;
//            int y = (int)(Math.random() * 500) - 250;
//            System.out.print(x);
//            Point position = new Point(x, y);
//            CivilAirport airport = new CivilAirport(position);
//            civilAirports.add(airport);
//        }
        CivilAirport airport = new CivilAirport(new Point(100, 100));
        civilAirports.add(airport);
        airport = new CivilAirport(new Point(-300, -300));
        civilAirports.add(airport);
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
