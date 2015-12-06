package controllers;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
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
        for (int i = 0; i < numberOfAirports; i++) {
            int x = (int)(Math.random() * 700);
            int y = (int)(Math.random() * 700);
            Point position = new Point(x, y);
            CivilAirport airport = new CivilAirport(position);
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
