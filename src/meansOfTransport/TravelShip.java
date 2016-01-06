package meansOfTransport;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import spawners.Harbor;
import travelDependency.Passenger;

import java.util.ArrayList;

/**
 * Created by kadash on 18.10.15.
 */
public class TravelShip extends Ship {
    private final String imagePath = "images/ship.png";
    private int maxPassengers;
    private int currentNumberOfPassengers;
    private ArrayList<Passenger> passengersOnBoard = new ArrayList<Passenger>();

    private String companyName;

    public TravelShip(ArrayList<Harbor> allDestination, Pane context, int id ) {
        super(allDestination, context);
        this.imageViewMeanOfTransport.setImage(new Image(imagePath));
        for (Harbor harbor : allDestination) {
            this.route.add(harbor);
        }
    }
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
