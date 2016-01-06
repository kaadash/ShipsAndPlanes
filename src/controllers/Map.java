package controllers;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
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
        generateMilitaryAirports(10, stack);
        generateCivilAirports(10, stack);
    }

    private static Point centralPoint = new Point(550, 350);

    public static ArrayList<Point> generateCordsInCircle(int numberOfAirports, int distance) {
        ArrayList<Point> cords = new ArrayList<Point>();
        double sizeOfStep = 2 * Math.PI/numberOfAirports;
        double angle = 0;

//        Generator of city in circle
        for (int i = 0; i < numberOfAirports; i++) {
            angle +=sizeOfStep;
// I know that for example A2 isn't name of variable, it is name of highway
            int y2 = (int)Math.floor( distance * Math.sin( angle + 0.10 ) );
            int x2 = (int)Math.floor( distance * Math.cos(angle + 0.10) );
            cords.add(new Point(x2, y2));

            int y = (int)Math.floor( distance * Math.sin( angle ) );
            int x = (int)Math.floor( distance * Math.cos( angle ) );
            cords.add(new Point(x, y));

            int y1 = (int)Math.floor( distance * Math.sin(angle + 0.2) );
            int x1 = (int)Math.floor( distance * Math.cos( angle + 0.2) );
            cords.add(new Point(x1, y1));
        }
        return cords;
    }

    public static void generateCivilAirports(int numberOfAirports, Pane context) {

        ArrayList<Point> cords = generateCordsInCircle(numberOfAirports, 250);
//        Adding cities into global civilAirports
        ArrayList<Point> leftTrackAirports = new ArrayList<Point>();
        ArrayList<Point> centerTrackAirports = new ArrayList<Point>();
        ArrayList<Point> rightTrackAirports = new ArrayList<Point>();

        int civilAirportLoopCounter = 0;

        for (Point cord: cords){
            civilAirportLoopCounter++;
            cord.setLocation(cord.getX() + centralPoint.getX(), cord.getY() + centralPoint.getY());
            int loopModulo = civilAirportLoopCounter % 3;
            switch (loopModulo) {
                case 0: leftTrackAirports.add(cord);
                    civilAirportLoopCounter = 0;
                    break;
                case 1: centerTrackAirports.add(cord);
                    CivilAirport airport = new CivilAirport(cord);
                    airport.draw(context, airport.getImagePath());
                    civilAirports.add(airport);
                    break;
                case 2: rightTrackAirports.add(cord);
                    break;
                default:
                    System.out.println("error");
                    break;
            }
        }
        civilAirportLoopCounter = 0;
        for(Point track : leftTrackAirports) {
            Point centerCord = centerTrackAirports.get(civilAirportLoopCounter);
            Point rightCord = rightTrackAirports.get(civilAirportLoopCounter);

            civilAirports.get(civilAirportLoopCounter).setLeftLaneStartingPoint(track);
            civilAirports.get(civilAirportLoopCounter).setRightLaneStartingPoint(rightCord);

            Point leftLineEndingPoint = new Point((int)(track.getX() + centralPoint.getX() - centerCord.getX()),
                    (int)(track.getY() + centralPoint.getY() - centerCord.getY()));

            Point rightLineEndingPoint = new Point( (int)(rightCord.getX() - centerCord.getX() + centralPoint.getX()),
                    (int)(rightCord.getY() - centerCord.getY() + centralPoint.getY()));

            civilAirports.get(civilAirportLoopCounter).setLeftLaneEndingPoint(leftLineEndingPoint);
            civilAirports.get(civilAirportLoopCounter).setRightLaneEndingPoint(rightLineEndingPoint);

            Line leftLine = new Line(track.getX(), track.getY(),
                    leftLineEndingPoint.getX(), leftLineEndingPoint.getY() );

            Line centerLine = new Line(centerCord.getX(), centerCord.getY(), centralPoint.getX(), centralPoint.getY());
            Line rightLine = new Line(rightCord.getX(), rightCord.getY(),
                    rightLineEndingPoint.getX(), rightLineEndingPoint.getY());
            context.getChildren().addAll(leftLine, centerLine, rightLine);
            civilAirportLoopCounter++;
        }
    }

    public static void generateMilitaryAirports(int numberOfAirports, Pane context) {
        ArrayList<Point> cords = generateCordsInCircle(numberOfAirports, 350);
//        Adding cities into global military airports
        for (Point cord: cords){
            cord.setLocation(cord.getX() + centralPoint.getX(), cord.getY() + centralPoint.getY());
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
