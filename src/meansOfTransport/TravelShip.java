package meansOfTransport;

import javafx.scene.layout.Pane;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by kadash on 18.10.15.
 */
public class TravelShip extends Ship {
    public TravelShip(ArrayList<Point> allDestination, Pane context) {
        super(allDestination, context);
    }
    private int maxPassengers;
    private int currentNumberOfPassengers;
    private String companyName;
    @Override
    public void run() {
        while(true){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
