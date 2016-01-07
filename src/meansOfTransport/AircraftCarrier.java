package meansOfTransport;

import javafx.scene.image.*;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import spawners.Airport;
import spawners.Harbor;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by kadash on 18.10.15.
 */
public class AircraftCarrier extends Ship {
    private String ammoType;
    private final String imagePath = "images/militarymightjl2.jpg";
    public AircraftCarrier(ArrayList<Harbor> allDestination, Pane context, int id) {
        super(allDestination, context);
        for (Harbor harbor : allDestination) {
            this.route.add(harbor);
        }
        this.imageViewMeanOfTransport.setImage(new Image(imagePath));
        this.ID = id;
    }
//    public MilitaryAircraft spawnMilitaryAircraft () {
//        return new MilitaryAircraft();
//    }

    private String generateCompanyName() {

        String companyName[] = {"Super", "Fine", "Hello", "FaceBook", "AreYouSure"};
        return companyName[(int)(Math.random() * companyName.length - 1)];
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
